with Ada.Text_IO;
use Ada.Text_IO;

procedure Main is
   N : Integer := 10;

   type isStop is
     array (1 .. N) of Duration;

   type BoolArr is
     array (1 .. N) of Boolean;
   CanStop : BoolArr := (others => False);
   pragma Volatile(CanStop);



    task type MyThread is
      entry Start(Id : in Integer);
   end MyThread;

   task body MyThread is
      Sum : Long_Long_Integer := 0;
      Id : Integer;
      Step: Long_Long_Integer := 0;

   begin
      accept Start (Id : in Integer) do
         MyThread.Id := Id;
      end Start;

      loop
         Sum := Sum + 2;
         Step:=Step+1;
         exit when CanStop(Id);
      end loop;
      Put_Line ("id:"&Id'Img & "  sum:" & Sum'Img&" step:"&Step'Img);

   end MyThread;

   task type BreakThread is
      entry Start(Timers : isStop);
   end BreakThread;

   task body BreakThread is
      Timers : isStop;
      Min : Duration;
      MinIndex : Integer;
   begin
      accept Start (Timers : isStop) do
         BreakThread.Timers := Timers;
      end Start;

      for I in Timers'Range loop

         for J in Timers'Range loop
            if Timers (J) = 0.0 then
               CanStop (J) := true;
            else
               Min := Timers (J);
               MinIndex := J;
            end if;
         end loop;


         for J in Timers'Range loop
            if Timers (J) /= 0.0
              and then Min > Timers (J) then
               Min := Timers (J);
               MinIndex := J;
            end if;
         end loop;

         for J in Timers'Range loop
            if Timers (J) > Duration(0) then
               Timers (J) := Timers (J) - Min;
            end if;
         end loop;

         delay Min;
       CanStop (MinIndex) := true;

      end loop;
   end BreakThread;



   Break : BreakThread;
   Tasks : array (1 .. N) of MyThread;

   Stop_Dur : isStop := (7.0, 5.0, 8.0, 6.0, 8.0 , 9.0, 12.0, 11.0, 13.0, 14.0);

begin
   Break.Start(Stop_Dur);

   for I in 1 .. N loop
      Tasks(I).Start(I);
   end loop;



end Main;
