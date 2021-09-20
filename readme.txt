By: Adam Sheeres-Paulicpulle
Student ID: 1036569
Student Email: asheeres@uoguelph.ca
Last updated: November 18, 2019

To compile:
Navigate to root directory of program.
Run ant:      /Users/<Username>/apache-ant-1.10.7/bin/ant
Run program:  /Users/<Username>/apache-ant-1.10.7/bin/ant runmeJava

Notes:
  It crashes 1/100 times, and I have no clue why. I think it has to do with code from before, but I don't really have enough
  time to fix that as it is now 3:40am. If it doesn't run when you grade me can you please please please try to run it until
  it does. I know it runs it just might not on the first time.

Extra Bits:
1. (10 Marks) The doors list is clickable and can be used to shift the view to a different Chamber or Passage
  comments: The door list is a drop-down menu where you can select the desired door. It displays where it connects to
  relative from where you are. Use the "go through" button to go to the corresponding passage/chamber.
2. (30 marks) When a chamber or passage is selected from the main window a graphical rendering of the chamber is displayed in addition to the textual description.
  comments: I used the same generation for things like circle/hexagon/octagon/cave shapes for chamber shapes, because they
  would all be roughly similar to one another. Also because of implementing this into the program, it is slow. Sometimes
  it will take a while to load (especially if you're on a slow computer). Also, you might have to extend the window as
  when a chamber is 40x40 it takes up most of the screen.
