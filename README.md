# CHERK readme
## Introduction
## Process
#### Arriving at our idea

#### Doing research
In order to create an algorithm to solve our problem, it was necessary to do some research, in order to better understand the necessary information to complete this project. The first part of our research was an interview with the AP Exam coordinator at GA, Ms. Burman. While interviewing Ms. Burman, our group asked her a series of questions, including how long it takes her to create an AP exam proctor schedule, what she looks for when deciding who proctors each AP exam, what teachers are able to proctor what exams, and if teachers are able to switch who proctors an exam at a certain point. Once she answered each of the questions in detail, we were able to begin to come up with a plan on how we wanted our program to work. We determined that teachers often proctor exams during their free blocks and teachers are able to switch off proctoring exams. Subsequently, we were able to create an algorithm that takes in all the AP teachers and their free blocks in one file and all the AP exams with their date and time in another file, and produce an exam proctoring schedule, that assigns teachers with free blocks that fall during the time of an exam to proctor them.  

##### DateTime Library
Originally we wanted to avoid using to many Java API's. These are packages that contain prewritten code that we can implement in our projects. However, reaserching these packages contain time, and we wanted to focus more on the actual planning and coding part. However, we needed to use the DateTime library when coding our project. We were able to familiarise ourselves with this by calculating time as the number of seconds after midnight. After much debate, we decided to convert back and forth between 24-hour time as this allowed us to distinguish between morning and afternoon. 

#### Creating big goals and outlining 
#### Execution
### Program Goals/Design
The primary goal of our project was to make Ms. Berman and our AP teachers' lives easier when it comes to scheduling proctors for AP exams. Our goal was to almost eliminate the tedious process that Ms. Berman undertakes every year when she matches AP teachers and their frees with when AP exams are. In an ordinary year, Ms. Berman would start with the AP exam time, look up the blocks that the AP Exam took place in, search througha lit of AP teachers to find those who have the blocks free, and then reach out to the teacher to ensure they did not have a conflict during this time. Other things to consider were if the teacher had young kids and needed to pick them up in the afternoon, if the teacher had alread proctored another exam, and if the department the taught in conflicted with the department that the AP Exam would fall under. We wanted to simplify this task by creating an AP Exam Schedule Maker to avoid the guess and check process by taking teacher's names, frees, and departments as well as the AP Exam schedule and returning the potential list of proctors for each exam that Ms. Berman could then use to determine who she would like to proctor.
### Methods
### Challenges

#### Different Branches
In the beginning of our programming portion, we ended up being on different branches of GitHub, with some of us on one branch and some on the other. This might be beneficial in other cases, but in our case it is not. Being on different branches meant we were essentially in two different workplaces, and our files were in two seperate places. Luckily, we managed to get on the same branch by having our teacher delete one brance and make the ofhter branch the default branch, which allower all of our files and commits to be seen in one place. 

#### Ms. Zhu's code
We ran into some problems when trying to implement our teachers code. Her code allowed us to enter in a letter block and a date and receive the start and end time of the block. However, in some of our methods we needed the block schedule with corresponding times for the entire day, not just for one block. Different days of the week have different schedules, and it was important to know the days schedule for our code to work as planned. Our teacher made code to do this, but:
1. it was an abstract class, meaning we could not make instances of the schedule, or in other words, it did not allow us to create a block schedule as we had hoped.
2. the method was private, meaning we could not access it outside of that class (aka file) it was in. 
Solution: I first tried making the method public so I could access it outside of that class/file, but that did not work because the class was still abstract. I ended up having to boorow some of the if statements my teacher wrote, modifying them, and initializing the schedule types outside Ms. Zhu's file. It has been a while since we have done inheritance, and that was the quickest solution for us. 


## Conclusion 
