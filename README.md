# Hotel_manager

There are several main purposes of the program. One of them is to track how
many people are already entered in the restaurant and accordingly to that
how many are left. That is possible by entering the room number of the guest 
then choosing the people who are entering at the moment. It not only gives
way more accuracy and clearer idea of how many people are already in, 
but also makes you faster in searching for the room and guests. 

As a result of this, the cooks will know if they have to prepare another 
batch of food for incomming guests and decide if there is a need of
this. Another useful function is searching for one specific guest by entering 
his name. For instance the receptionists want to find someone in the restaurant, 
it will be way easier. 

The program works with CSV files, which are exproted from hotel software. 
These CSV files contains all data for the rooms and guests, which 
the program needs in order to work properly. 

When the program is started, its' first work will be to check if there is already 
created file in the directory, which is set when opening the program for the first
time. 

If there is no chosen directory or the program is used for the first time, it asks
the user to choose one and from that moment it will be used as a default. 

In that directory folders with date as a folders name will be created every day
when the program is opened. In these folders, PDF files will be created after
every meal.  

There are 2 options for exporting the PDF file. First is to export it in already 
created folder when opening the program at that date. The file will be exported 
and saved automatically with the name of the meal, according to what time is 
exported. Seccond option is to export the file in another directory with other 
name of the PDF file. 

## Step 1
 
<div style="display: flex; align-items: center;">
   <h3>1. Choosing a default directory in case opening for the first time</h3>
  <img src="images/1%20choose%20directory.png" alt="1" width="400">
      <p> </p> 
  <img src="images/2%20Set%20defauld%20directory.png" alt="2" width="400" style="margin: 0 50px;">
 <p> </p> 
      <h4> 1.1 After successful setting up the default directory, folder is created</h4> 
 a) <img src="images/3%20New%20directory%20SET%20Message.png" alt="3" width="310">
 b) <img src="images/4%20New%20file%20created.png" alt="4" width="300">
</div>

## Step 2

<div style="display: flex; align-items: center;">
    <h3>2. The main window of the program before importing CSV file with data of the guests</h3>
  <img src="images/5%20The%20main%20app%20window.png" alt="5" width="600">
        <h4> 2.1 Importing the CSV file, exported from the hotel software</h4> 
   <img src="images/6%20Choose%20CSV%20file.png" alt="2" width="400" style="margin: 0 50px;">
   <img src="images/6.1%20Chosen%20CSV%20message.png" alt="3" width="400">
</div>


