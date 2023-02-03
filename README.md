# CHERK readme
## Introduction
Each spring, the College Board administers a set of AP Exams to test student's knowledge in various subject areas. Here at GA, we offer many AP classes, and scheduling the exams is a difficult and tedious process. There are many moving parts, such as coordinating times proctors are free with the times of the exams. We wanted to help simplify this process, and we feel that our code can benefit multiple users. 

## Process

#### Arriving at our idea
We originally brainstormed many possible ideas for our project, which were focused on the goal of improving an aspect of our school. Early ideas included a map app to help new students navigate our school and a carpool matchup program. However, we narrowed down our possible candidates to either helping college counselors schedule meetings or schedule AP exams for proctors who are available to proctor them. After looking at both possibilities, we realized that the AP exam schedule was a more difficult problem, as coordinating teachers' free blocks with AP Exam times is more difficult than simply matching up teachers and students by their free blocks. The AP Exam project would be a more appropriate and challenging project for five people, so we decided to go with that. 

#### Doing research
To create an algorithm to solve our problem, it was necessary to do some research, to better understand the necessary information to complete this project. The first part of our research was an interview with the AP Exam coordinator at GA, Ms. Berman. While interviewing Ms. Berman, our group asked her a series of questions, including how long it takes her to create an AP exam proctor schedule, what she looks for when deciding who proctors each AP exam, what teachers can proctor what exams, and if teachers can switch who proctors an exam at a certain point. Once she answered each of the questions in detail, we were able to begin to come up with a plan for how we wanted our program to work. We determined that teachers often proctor exams during their free blocks and teachers can switch off proctoring exams. Subsequently, we were able to create an algorithm that takes in all the AP teachers and their free blocks in one file and all the AP exams with their date and time in another file and produces an exam proctoring schedule, that assigns teachers with free blocks that fall during the time of an exam to proctor them.  

##### DateTime Library
Originally we wanted to avoid using too many Java APIs. These are packages that contain prewritten code that we can implement in our projects. However, researching these packages could take a while, and we wanted to focus more on the actual planning and coding part. However, we needed to use the DateTime library when coding our project. We were able to familiarise ourselves with this by calculating time as the number of seconds after midnight. After much debate, we decided to convert back and forth between 24-hour time as this allowed us to distinguish between morning and afternoon. We also learned that the date should be in a specific string format for it to be parsed correctly into a LocalDate object. 

#### Creating big goals and outlining 
We used a Google spreadsheet to manage our tasks. We thought of our code broadly at first, outlining larger tasks that we wanted our code to execute and breaking it down into smaller parts. This top-down design helped us better organize and plan out our code and allowed us to better understand what exactly we were trying to accomplish. We then wrote out an algorithm for segments of code, which detailed the steps we would take to accomplish a task. Writing out algorithms made the coding itself more manageable and less confusing. While writing the algorithms for the main chunks of our code we also identified possible helper methods or pieces of code that were used multiple times across the programs that we could write into a method to reuse and therefore simplify our code. By planning out our code very carefully, our process was very efficient. However, we remained flexible, and we would change our spreadsheet plan depending on our circumstances. For example, we realized that the code used to scan both the exam file and teacher files was similar, so instead of writing two separate methods, we combined them into one. By having a well-structured and organized plan but also remaining adaptable, we were able to plan our project efficiently. 

#### Execution
Once we had our algorithms down, we were able to code them quickly. However, when running them, we had many errors. A lot of them were problems with our syntax, and we had a lot of coding "typos". Those were pretty easy to fix, and once we resolved those issues we dealt with run-time errors, which are harder to fix as they involve trying to access a variable that might not exist, which forced us to read over our code carefully and conceptualize what was happening. This is where iterative design comes in. We would run our code and try to see exactly what line(s) of code were causing our errors. After the methods were coded, we would often use iterative design, or in other words, trial, and error, to see what parts worked and what didn't. For the parts that did not work, it was usually a simple fix but one that took some time to arrive at. Other times, the issue was larger and we might have to rewrite a couple of lines of code. If the entire method was unsalvagable, as a last resort we would go back to outlining a new algorithm. After our code ran successfully, we quickly went to work testing it with different inputs, to ensure our code was robust. This process took a while and was also iterative, as code that works for simple tests may not work with larger ones. Debugging these errors was difficult because it can be hard to see why some inputs work and not others. 


### Program Goals/Design
The primary goal of our project was to make Ms. Berman and our AP teachers' lives easier when it comes to scheduling proctors for AP exams. Our goal was to almost eliminate the tedious process that Ms. Berman undertakes every year when she matches AP teachers and their frees with when AP exams are. In an ordinary year, Ms. Berman would start with the AP exam time, look up the blocks that the AP Exam took place in, search through a list of AP teachers to find those who have the blocks free, and then reach out to the teacher to ensure they did not have a conflict during this time. Other things to consider were if the teacher had young kids and needed to pick them up in the afternoon if the teacher had already proctored another exam, and if the department they taught conflicted with the department that the AP Exam would fall under. We wanted to simplify this task by creating an AP Exam Schedule Maker to avoid the guess and check process by taking teacher's names, frees, and departments as well as the AP Exam schedule and returning the potential list of proctors for each exam that Ms. Berman could then use to determine who she would like to proctor.
### Methods

#### Helper Methods

##### convertStandardtoMilitary and convertMilitaryToStandard
helped us convert between 12 hour and 24 hour time. Our file contains time in the 12 hour format because that is what we use day-to-day, but converting it into military time makes it easier for our code to compare times are before and after each other. We intrinsically distinguish between a.m. and p.m., but our coumputers don't. 

##### sort
takes a list of LocalTime objecta and puts them in order because, again, computers don't intrinsically know the order time occurs. 





#### Main Methods



### Challenges

#### Different Branches
At the beginning of our programming portion, we ended up being on different branches of GitHub, with some of us on one branch and some on the other. This might be beneficial in other cases, but in our case, it is not. Being on different branches meant we were essentially in two different workplaces, and our files were in two separate places. Luckily, we managed to get on the same branch by having our teacher delete one branch and make the other branch the default branch, which allowed all of our files and commits to being seen in one place. 

#### Ms. Zhu's code
We ran into some problems when trying to implement our teacher's code. Her code allowed us to enter a letter block and a date and receive the start and end times of the block. However, in some of our methods, we needed the block schedule with corresponding times for the entire day, not just for one block. Different days of the week have different schedules, and it was important to know the day's schedule for our code to work as planned. Our teacher made code to do this, but:
1. it was an abstract class, meaning we could not make instances of the schedule, or in other words, it did not allow us to create a block schedule as we had hoped.
2. the method was private, meaning we could not access it outside of that class (aka file) it was in. 
Solution: I first tried making the method public so I could access it outside of that class/file, but that did not work because the class was still abstract. I ended up having to borrow some of the if statements my teacher wrote, modifying them, and initializing the schedule types outside Ms. Zhu's file. It has been a while since we have done inheritance, and that was the quickest solution for us. 


## Conclusion 
