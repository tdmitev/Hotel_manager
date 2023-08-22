# Hotel_manager

There are several main purposes of the program. One of them is to track how
many people are already entered in the restaurant and accordingly to that
how many are left. That is possible by entering the room number of the guest 
then choosing the people who are entering at the moment. It not only gives
way more accuracy and clearer idea of how many peaple are already in, 
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
when the program is opened. In these folders, PDF files, will be created after
every meal. 

There are 2 options for exporting the PDF file. First is to export it in already 
created folder when opening the program at that date. The file will be exported 
and saved automatically with the name of the meal, according to what time is 
exported. Seccond option is to export the file in another directory with other 
name of the PDF file. 
