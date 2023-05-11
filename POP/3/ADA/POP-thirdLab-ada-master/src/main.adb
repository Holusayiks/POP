with Ada.Text_IO, GNAT.Semaphores;
use Ada.Text_IO, GNAT.Semaphores;

with Ada.Containers.Indefinite_Doubly_Linked_Lists;
use Ada.Containers;

procedure Main is
   package String_List is new Indefinite_Doubly_Linked_Lists (String);
   use String_List;

   procedure Storage(
                     storage_size,
                     consumer_count,
                     producer_count,
                     consumer_product_amount,
                     producer_product_amount: in Integer) is

      Storage : List;

      access_storage :  Counting_Semaphore (1, Default_Ceiling);
      empty_storage :  Counting_Semaphore (0, Default_Ceiling);
      full_storage :  Counting_Semaphore (storage_size, Default_Ceiling);

      task type Consumer is
         entry Start (id : in Integer);
      end Consumer;

      task type Producer is
         entry Start (id : in Integer);
      end Producer;


      task body Consumer is
         id : Integer;
      begin

         accept Start ( id : in Integer) do
            Consumer.id := id;
         end Start;

         for i in 1..consumer_product_amount loop

            Put_Line("Consumer #" & id'Img & " see if the storage is empty.");
            empty_storage.Seize;
            Put_Line("Consumer #" & id'Img & " near the storage for item #" & i'img);
            access_storage.Seize;
            Put_Line("Consumer #" & id'Img & " in the storage for item #" & i'Img);

            declare
               item : String := First_Element (Storage);
            begin
               Put_Line ("Consumer #" & id'Img & " take the item : " & item);
            end;

            storage.Delete_First;

            Put_Line("Consumer #" & id'Img & " near the exit with item # " & i'Img);
            access_storage.Release;
            Put_Line("Consumer #" & id'Img & " left the storage with item # " & i'Img);
            full_storage.Release;
            Put_Line("The storage is no longer full thanks to consumer #" & id'Img & ".");
         end loop;

      end Consumer;

      task body Producer is
         id : Integer;
      begin

         accept Start (id : in Integer) do
            Producer.id := id;
         end Start;

         for i in 1 .. producer_product_amount loop

            Put_Line("Producer #" & id'Img & " look if the storage is full " & i'Img);
            full_storage.Seize;
            Put_Line("Producer #" & id'Img & " near the storage with item # " & i'Img);
            access_storage.Seize;
            Put_Line("Producer #" & id'Img & " in the storage with item # " & i'Img);

            storage.Append ("item " & i'Img);
            Put_Line ("Producer #" & id'Img & " put item : " & i'Img);

            Put_Line("Producer #" & id'img & " near the exit after putting item # " & i'Img);
            access_storage.Release;
            Put_Line("Producer #" & id'Img & " Left the storage after putting item # " & i'Img);
            empty_storage.Release;
            Put_Line("The storage is no longer empty thanks to producer #" & id'Img & ".");
         end loop;

      end Producer;

      consumers : array (1..consumer_count) of Consumer;
      producers : array (1..producer_count) of Producer;

   begin

      for i in 1..consumer_count loop
         consumers(i).Start(id => i);
      end loop;

      for i in 1..producer_count loop
         producers(i).Start(id => i);
      end loop;

   end Storage;

begin
   Storage(storage_size            => 12,
           consumer_count          => 6,
           producer_count          => 4,
           consumer_product_amount => 2,
           producer_product_amount => 3);
end Main;
