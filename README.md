# CHERK readme
## Introduction
Each spring, the College Board administers a set of AP Exams to test student's knowledge in various subject areas. Here at GA, we offer many AP classes, and scheduling the exams is a difficult and tedious process. There are many moving parts, such as coordinating times proctors are free with the times of the exams. We wanted to help simplify this process, and we feel that our code can benefit multiple users. Our code reads in two files, one of the exams and one of the teachers, and assigns a list of potential proctors for each exam based on their time availability and department.

## Process
#### Arriving at our idea
We originally brainstormed many possible ideas for our project, which were focused on the goal of improving an aspect of our school. Early ideas included a map app to help new students navigate our school and a carpool matchup program. However, we narrowed down our possible candidates to either help college counselors schedule meetings or schedule AP exams for proctors who are available to proctor them. After looking at both possibilities, we realized that the AP exam schedule was a more difficult problem, as coordinating teachers' free blocks with AP Exam times is more difficult than simply matching up teachers and students by their free blocks. The AP Exam project would be a more appropriate and challenging project for five people, so we decided to go with that. 

#### Doing research
To create an algorithm to solve our problem, it was necessary to do some research, to better understand the necessary information to complete this project. The first part of our research was an interview with the AP Exam coordinator at GA, Ms. Berman. While interviewing Ms. Berman, our group asked her a series of questions, including how long it takes her to create an AP exam proctor schedule, what she looks for when deciding who proctors each AP exam, what teachers can proctor what exams, and if teachers can switch who proctors an exam at a certain point. Once she answered each of the questions in detail, we were able to begin to come up with a plan for how we wanted our program to work. We determined that teachers often proctor exams during their free blocks and teachers can switch off proctoring exams. Subsequently, we were able to create an algorithm that takes in all the AP teachers and their free blocks in one file and all the AP exams with their date and time in another file and produces an exam proctoring schedule, that assigns teachers with free blocks that fall during the time of an exam to proctor them.  

##### DateTime Library
Originally we wanted to avoid using too many Java APIs. These are packages that contain prewritten code that we can implement in our projects. However, researching these packages could take a while, and we wanted to focus more on the actual planning and coding part. However, we needed to use the DateTime library when coding our project. We were able to familiarize ourselves with this by creating LocalTime objects and using helper methods built into this library such as isBefore() and isAfter() which gave us very useful information about how time blocks overlapped. After much debate, we decided to convert back and forth between 24-hour time as this allowed us to distinguish between morning and afternoon (military to standard time conversion and standard to military time conversion). We found that some of the more unique challenges in this project were simply ensuring that we were using values/datatypes that would be accepted by each function- We also learned that the date and time needed to be in a specific string format for it to be parsed correctly into a LocalDate object and our adjusted version of Mrs.Zhu's method getTimeFromBlockAndDate(). 

#### Creating big goals and outlining 
We used a Google spreadsheet to manage our tasks. We thought of our code broadly at first, outlining larger tasks that we wanted our code to execute and breaking it down into smaller parts. This top-down design helped us better organize and plan out our code and allowed us to better understand what exactly we were trying to accomplish. We then wrote out an algorithm for segments of code, which detailed the steps we would take to accomplish a task. Writing out algorithms made the coding itself more manageable and less confusing. While writing the algorithms for the main chunks of our code we also identified possible helper methods or pieces of code that were used multiple times across the programs that we could write into a method to reuse and therefore simplify our code. By planning out our code very carefully, our process was very efficient. However, we remained flexible, and we would change our spreadsheet plan depending on our circumstances. For example, we realized that the code used to scan both the exam file and teacher files was similar, so instead of writing two separate methods, we combined them into one. By having a well-structured and organized plan but also remaining adaptable, we were able to plan our project efficiently. WE found that one of our most helpful tools in this project was whitboarding our ideas- by translating our understanding in code to visual representations of what was happening in our code, we were able to break down large task into more maneagable ones. 

Some of our Process in action:
![Cherk1](cherk1.jpeg)
![Cherk2](cherk2.jpeg)

#### Execution
Once we had our algorithms down, we were able to code them quickly. However, when running them, we had many errors. A lot of them were problems with our syntax, and we had a lot of coding "typos". Those were pretty easy to fix, and once we resolved those issues we dealt with run-time errors, which are harder to fix as they involve trying to access a variable that might not exist, which forced us to read over our code carefully and conceptualize what was happening. This is where iterative design comes in. We would run our code and try to see exactly what line(s) of code were causing our errors. After the methods were coded, we would often use iterative design, or in other words, trial, and error, to see what parts worked and what didn't. For the parts that did not work, it was usually a simple fix but one that took some time to arrive at. Other times, the issue was larger and we might have to rewrite a couple of lines of code. If the entire method was unsalvagable, as a last resort we would go back to outlining a new algorithm. After our code ran successfully, we quickly went to work testing it with different inputs, to ensure our code was robust. This process took a while and was also iterative, as code that works for simple tests may not work with larger ones. Debugging these errors was difficult because it can be hard to see why some inputs work and not others. 


### Program Goals/Design
The primary goal of our project was to make Ms. Berman and our AP teachers' lives easier when it comes to scheduling proctors for AP exams. Our goal was to almost eliminate the tedious process that Ms. Berman undertakes every year when she matches AP teachers and their frees with when AP exams are. In an ordinary year, Ms. Berman would start with the AP exam time, look up the blocks that the AP Exam took place in, search through a list of AP teachers to find those who have the blocks free, and then reach out to the teacher to ensure they did not have a conflict during this time. Other things to consider were if the teacher had young kids and needed to pick them up in the afternoon if the teacher had already proctored another exam, and if the department they taught conflicted with the department that the AP Exam would fall under. We wanted to simplify this task by creating an AP Exam Schedule Maker to avoid the guess and check process by taking teacher's names, frees, and departments as well as the AP Exam schedule and returning the potential list of proctors for each exam that Ms. Berman could then use to determine who she would like to proctor.

### Methods

#### Helper Methods

##### convertStandardtoMilitary and convertMilitaryToStandard
- helped us convert between 12-hour and 24-hour time. Our file contains time in the 12-hour format because that is what we use day-to-day, but converting it into military time makes it easier for our code to compare times before and after each other. We intrinsically distinguish between a.m. and p.m., but our computers don't. 

##### sort
- takes a list of LocalTime objects and puts them in order because, again, computers don't intrinsically know the order that time occurs. 

#### generateListsOfObjects
- Using a List of files and two Lists, one of Teacher objects and one of  Exam objects, this method goes through the file line by line and adds Teacher and Exam objects to the teacher and exam lists. The teacher and exam objects make it easy for us to hold and access information. We first created a for loop because we wanted the code in the method to run multiple times, one for each file in the list of files We then made a while loop that iterated through each line of text in the file, and a 2d array list of strings called instanceVariables that we would later pass into either a teacher or exam constructor. We substring-ed through each line of the file and performed different actions depending on the format of the string. Commas in the file separated the different categories of information we needed as instance variables, and quotations enclosed the categories that could have more than one option, such as the department a teacher was in or their free blocks. If a substring contained quotations, we split it up into an ArrayList of multiple indices, and if not, we made a one-element array list; regardless we added this ArrayList to our instanceVariables ArrayList that we passed into a teacher or exam object constructor as we finished iterating through each line. 

##### consolidateFrees
- This helper method takes in a teacher object and a String date and returns an ArrayList of ArrayLists of LocalObjects. What this method does is it takes a teacher's schedule on a particular day and consolidates adjacent blocks into larger time blocks so that we can compare a large free time block to an exam which takes a long time. To do this, we first created an arraylist of all of the letter block frees that the inputted teacher had on that day. Then, we created an arraylist of arraylists of localtime objects to hold the start and end time of each block so that the structure looked like this --> [[ABlockLocalTimeStart,ABlockLocalTimeEnd],[BBlockLocalTimeStart,BBlockLocalTimeEnd],[DBlocklocalTimeStart, DBlockLocalTimeEnd]] (Assuming the teachers frees on that day were A, B, and D). Then, we sorted the time blocks in chronological order- we realized this was a vital step because in a given day G block could occur before C block for example and our code would not account for that. We used the sort() helper method to sort in chronological order. Finally, we compared the start and end times of adjacent blocks, looping through each of the blocks and comparing if they were close enough and removed duplicate times. If they were adjacent, we would make the new consolidated block the start time of the first block and the end time of the second. We would iterate through our loop again and continue consolidating. At the end we returned the consolidated blocks in an arraylist of arraylists of localTime objects.

##### containsTime
- containsTime returns true if a teacher's consolidated blocks can proctor an exam. It returns true if the teacher can proctor the entire exam or if their entire free time is within the exam time frame. It also returns true it a teacher can proctor for 30 mins or more at the beginning or the end of the exam. 
##### getTimeFromBlockAndDate 
- This helper method was borrowed from Mrs. Zhu's code which takes in a date of the exam and a block on that day and returns an arraylist of two localtime obects- the start time and the end time. 

##### arrayFormat
- This helper method is used in the main writeIn function, to correctly format an array to be printed. This function takes an an ArrayList of Strings and returns a String that contains all of the elements, each separated by a comma, to print it into the .csv of the exam proctor schedule in a more readable fashion. 

##### getUSScheduleForDate
- This helper method was one Mrs. Zhu created to help the getTimeFromBlockAndDate method. It takes in a date and returns a UsSchedule object which holds all of the frees on a day. Mrs. Zhu did extensive coding for the objects USSchedule and it's object's methods like .getDayType(), .adjustedSchedules(), .parse(), and other helepr emthods for the object that made her able to write this helper method and .getTimeFromBlockAndDate().
---
#### Main Methods

##### matchUp
Using the output from generateListsOfObjects(), matchUp() was used to create a map, holding the keys as Strings, which are examNames, and values as ArrayLists of Strings, which are a list of proctors. To do this, we first initialize an empty map with Strings as keys and ArrayLists of Strings as values. Then, created an outer loop, that would loop through the exam objects, to look at each exam individually. Within this loop, we then cloned the list of teacher objects, to be able to edit this list of each specific exam, without altering the main list. Then we stored the date, department, and time of the exam, to be used later. After this, we decided to loop through the cloned list of teachers and remove all teachers that are in the same department as the department of the exam. Then, after this loop, we are going to loop through the newly updated teacher list and first use the consildateFrees() to consolidate the frees and convert them to LocalTime objects and return an ArrayList of ArrayLists of Strings. This will hold both the start time and end time of each group of frees for the teacher that is being looked at. Then, once this list is made, we used the helper method containsTime() to determine if a teacher’s free block(s) are contained in the exam time, meaning they can proctor at least part of this exam. Then, this time is removed from the total exam time. For example, if Ms. Z is free from 10 am - 12 pm, and the exam time is 10 am - 1 pm, by removing the time Ms. Z is proctoring, the new exam time that we must find a proctor for is 12 pm-1 pm. This loop will continue until the teacher cannot proctor another time, and will move on to the next exam once there is no remaining time left to be proctored for this exam. Finally, once the proctors are determined for an exam, the name and the list of proctors will be added to the map that was initialized at the beginning of the function, and this map will eventually be returned. 

##### writeIn
The final main method that was used was the WriteIn function. This takes in the map that is generated in the matchUp() function and the list of AP exams, and formats it correctly to be written into a .cvs, which will hold the exam name, date, and time, and will use the helper method arrayFormat to correctly format the list of exam proctors to be printed. 

### Challenges
Throughout our project, we certainly came across some challenges. The first challenge that we faced early on was figuring out how to determine what time a teacher's free blocks fell on a given day to determine if they are available to be a proctor for an exam that day. Thankfully, Ms. Zhu had already written code to convert a block and date to a time on that date, so she allowed us to implement her code in our project; we explain this process in more detail below. Another challenge we faced throughout our project was familiarizing ourselves with the DateTime library since we needed to use LocalTime objects to hold times. Cassidy did research and developed a strong understanding of the library, its methods, and its syntax and relayed this knowledge to the group to overcome this issue. An additional challenge we had to navigate was deciding if it was simpler to convert exam times to blocks or convert teacher-free blocks to time to match teachers with exams to the proctor. We broke down this problem as a group and discussed the pros and cons of both ways. Ultimately, we decided that converting blocks to times, so that we were using LocalTime objects in our main matchUp function, was more effective because exam times don't correspond directly with block times. By using LocalTime objects, we also had the added benefit of being able to more easily sort blocks (corresponding to times) on a given day in ascending order.

#### Different Branches
At the beginning of our programming portion, we ended up being on different branches of GitHub, with some of us on one branch and some on the other. This might be beneficial in other cases, but in our case, it is not. Being on different branches meant we were essentially in two different workplaces, and our files were in two separate places. Luckily, we managed to get on the same branch by having our teacher delete one branch and make the other branch the default branch, which allowed all of our files and commits to being seen in one place. 

#### Ms. Zhu's code
We ran into some problems when trying to implement our teacher's code. Her code allowed us to enter a letter block and a date and receive the start and end times of the block. However, in some of our methods, we needed the block schedule with corresponding times for the entire day, not just for one block. Different days of the week have different schedules, and it was important to know the day's schedule for our code to work as planned. Our teacher made code to do this, but:
1. it was an abstract class, meaning we could not make instances of the schedule, or in other words, it did not allow us to create a block schedule as we had hoped.
2. the method was private, meaning we could not access it outside of that class (aka file) it was in. 
Solution: I first tried making the method public so I could access it outside of that class/file, but that did not work because the class was still abstract. I ended up having to borrow some of the if statements my teacher wrote, modifying them, and initializing the schedule types outside Ms. Zhu's file. It has been a while since we have done inheritance, and that was the quickest solution for us. 

#### Human Touch
While interviewing Ms. Berman, it became apparent that a large element of the scheduling process is the "human touch" that she is able to add. For example, she knows if a teacher has a kid that needs to be picked up at 3pm, and therefore wouldn't schedule them to proctor an afternoon exam that goes until 3:30 or 4. Of course, it is very difficult for the computer to know this, which made our problem slightly more challenging. 

## Conclusion 
This was the biggest project we've done in class this year, and we learned a lot about communication and working as a team. We learned about choosing a project that was the right difficulty level, and we had to learn how to agree, disagree, and ask questions when necessary. GitHub was also a large part of our communication, and in class, we had to make sure no one was working on the same thing and that we were not editing code that someone else was working on to avoid merge conflicts (they still happened anyway). We also learned how to plan as a team, and we learned that writing out diagrams on whiteboards was a great way to visualize what was happening. We grew relied on planning and testing our code, often testing our code as soon as it was finished before we moved on to another large task. However, as helpful as this was, we also realized that we had to be flexible. Changing due dates that no longer suited us and sometimes going ahead and coding a main method even when the helper method was not quite right made us realize that we needed a combination of both top-down (planning) and iterative coding. We tried staying as organized as possible, but sometimes things got a little disorganized, and we learned to adapt to our circumstances and accommodate mishaps along the way. All in all,  planning and staying as organized as possible is what helped us stay on track, but we also learned to go with the flow and trust that everything would work out in the end. 
