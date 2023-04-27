with Ada.Text_IO; use Ada.Text_IO;
with Ada.Real_Time; use Ada.Real_Time;

procedure Main is

   dim : constant Integer := 10000;
   thread_num : constant Integer := 11;

   arr : array(1..dim) of Integer;

   procedure Init_Arr is
   begin
      for i in 1..dim loop
         arr(i) := i;
      end loop;
      arr(dim / 3) := -100;
   end Init_Arr;

   procedure part_min(start_index, finish_index : in Integer;
                      min, min_index : out Integer) is
   begin

      for i in start_index..finish_index loop
         if (min > arr(i)) then
            min := arr(i);
            min_index := i;
         end if;
      end loop;
   end part_min;

   protected part_manager is
      procedure set_part_min(min, min_index : in Integer);
      entry get_min(min, min_index : out Integer);
   private
      tasks_count : Integer := 0;
      min : Integer := arr(1);
      min_index : Integer := 1;
   end part_manager;

   protected body part_manager is
      procedure set_part_min(min, min_index : in Integer) is
      begin
         if (min < part_manager.min) then
            part_manager.min := min;
            part_manager.min_index := min_index;
         end if;
         tasks_count := tasks_count + 1;
      end set_part_min;

      entry get_min(min, min_index : out Integer) when tasks_count = thread_num is
      begin
         min := part_manager.min;
         min_index := part_manager.min_index;
      end get_min;

   end part_manager;

   task type starter_thread is
      entry start(start_index, finish_index : in Integer);
   end starter_thread;

   task body starter_thread is
      min : Integer;
      min_index : Integer;
      start_index, finish_index : Integer;
   begin
      accept start(start_index, finish_index : in Integer) do
         starter_thread.start_index := start_index;
         starter_thread.finish_index := finish_index;
      end start;

      part_min(start_index  => start_index,
               finish_index => finish_index,
               min => min,
               min_index => min_index);

      part_manager.set_part_min(min, min_index);
   end starter_thread;

   thread : array (1 .. thread_num) of starter_thread;

   procedure parallel_min(min, min_index : out Integer) is
   begin
      for i in 1..thread_num loop
         thread(i).start((i - 1) * dim / thread_num + 1,
                    i * dim / thread_num);
      end loop;
      part_manager.get_min(min, min_index);
   end parallel_min;

   min, min_index : Integer;
   Start_Time, Stop_Time : Time;
   Elapsed_Time          : Time_Span;
begin

   Init_Arr;
   Start_Time := Clock;
   parallel_min(min, min_index);
   Stop_Time := Clock;
   Elapsed_Time := Stop_Time - Start_Time;
   Put_Line(min'img);
   Put_Line(min_index'img);
   Put_Line ("time: "
             & Duration'Image (To_Duration (Elapsed_Time))
             & " ms");
end Main;
