Mauricio Renon
ICS432
Programming Assignment - Java Synchronization

*******************************************************************
****     QUESTION 2, EXERCISE 2: SEQUENTIALIMAGEPROCESSOR      ****
****ONLY TESTED A TOTAL OF 72 IMAGES OUT OF THE WHOLE .ZIP FILE****
*******************************************************************

///////////////////////////
//Filter I/O intesiveness//
///////////////////////////
1(lowest) - 4(highest)
            read  +  write = total I/O
1) INVERT (23.520 + 23.654 = 47.174 sec.)
2) SMEAR  (39.240 + 39.658 = 78.898 sec.)
3) OIL1   (186.283 + 190.822 = 377.105 sec.)
4) OIL6   (363.741 + 373.093 = 736.834 sec.)


//////////////////////
//SEQUENTIAL: INVERT//
//////////////////////

rpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpw
Time Spent Reading: 23.520sec.
Time Spent Processing: 23.831sec.
Time Spent Writing: 23.654sec.
Overall Execution Time: 71.005sec.

total I/O: (23.520 + 23.654 = 47.174 sec.)

//////////////////////
//CONCURRENT: SMEAR //
//////////////////////

rpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpw
Time Spent Reading: 39.240sec.
Time Spent Processing: 39.887sec.
Time Spent Writing: 39.658sec.
Overall Execution Time: 118.785sec.

total I/O: (39.240 + 39.658 = 78.898 sec.)

//////////////////////
//CONCURRENT: OIL1  //
//////////////////////

rpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpw
Time Spent Reading: 186.283sec.
Time Spent Processing: 191.765sec.
Time Spent Writing: 190.822sec.
Overall Execution Time: 568.870sec.

total I/O: (186.283 + 190.822 = 377.105 sec.)

//////////////////////
//CONCURRENT: OIL6  //
//////////////////////

rpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpwrpw
Time Spent Reading: 363.741sec.
Time Spent Processing: 374.984sec.
Time Spent Writing: 373.093sec.
Overall Execution Time: 1111.818sec.

total I/O: (363.741 + 373.093 = 736.834 sec.)

*******************************************************************
****     QUESTION 3, EXERCISE 2: CONCURRENTIMAGEPROCESSOR      ****
****ONLY TESTED A TOTAL OF 72 IMAGES OUT OF THE WHOLE .ZIP FILE****
*******************************************************************

//////////////////////
//CONCURRENT: INVERT//
//////////////////////

rrrrprpwwprwpwrpwrpwrprwprwprwrpwrrprwprwpwrpwrprwrrprwrprwprwprwprwprwprwprwpwrpwrpwprwprwprwrprwprwprwprwprwprwprwprpwrwprwprwprwpwprwprwpwrpwpwrprwrpwrpwprwprwrrprwprwprwprwprwprwprpwwrpwrpwrpwrprpwpwwpwpwpwpwpwpw
Time Spent Reading: 10.346 sec.
Time Spent Processing: 14.32 sec.
Time Spent Writing: 6.697 sec.
Overall Execution Time: 14.489 sec.

total I/O: 17.043 sec.

71.005sec./14.489sec. 
4.9x increase in performance

//////////////////////
//CONCURRENT: SMEAR //
//////////////////////

rrrrrrpwrrpwrpwprwpwrprwrrprwprwprwprwprwprwprwprwprwprwprwprwprwprwprwprwpwrpwrprwprwprwprwprwprwprwprwprwprwprwprwprwprwprwprwprwprwprwprwpwrpwprwrprwprwprwprwprwprwprwprwprwprwprwprwprwprwprwpwrprwpwpwpwpwpwpwpwpw
Time Spent Reading: 12.085 sec.
Time Spent Processing: 21.255 sec.
Time Spent Writing: 6.887 sec.
Overall Execution Time: 21.37 sec.

total I/O: 18.972 sec.

118.785sec./21.37sec.
5.56x increase in performance

//////////////////////
//CONCURRENT: OIL1  //
//////////////////////

rrrrrrrrprwprwprwprwprwprwprwprwprwprwprwprwprwprwprwprwprwprwprwprwprwprwprwpwrpwprwprwpwrpwrprwrprwprwprwprwprwprwprwprwprwprwprwprwprwprwpwrpwrpwrprwprwprwprwprwprwprwprwprwprwprwprpwwrprwprwprwprwpwpwpwpwpwpwpwpw
Time Spent Reading: 63.629 sec.
Time Spent Processing: 175.119 sec.
Time Spent Writing: 14.462 sec.
Overall Execution Time: 175.239 sec.

total I/O: 78.091 sec.

568.870sec./175.239sec.
3.25x increase in performance

//////////////////////
//CONCURRENT: OIL6  //
//////////////////////

rrrrrrrrprwprwprwprwpwprwrprwprwprwprwprwprwprwprwprwprwprwprprwpwrwprwprwprwpwrprwpwrpwrpwrprwprwprwprwprwprwprwprwprwprwprwpwrpwrpwrprwprwprwpwrprwprwprwprwprwprwprwprwprwprwprwprwprwprwprwprwprwprwpwpwpwpwpwpwpwpw
Time Spent Reading: 119.693 sec.
Time Spent Processing: 358.467 sec.
Time Spent Writing: 31.742 sec.
Overall Execution Time: 358.586 sec.

total I/O: 151.435 sec.

1111.818sec./358.586sec.
3.1x increase in performance

*******************************************************************
****               QUESTION 4, EXERCISE 2: REPORT              ****
****ONLY TESTED A TOTAL OF 72 IMAGES OUT OF THE WHOLE .ZIP FILE****
*******************************************************************

1) I would like to say that there is a correlation to performance gain when it is less I/O intensive and more computation intensive. And it it holds true in the case of OIL1 and OIL6. The lower the time for I/O lead to more performance gain. For example, OIL6 in the sequential had 1111.818/358.586 overall execution time which led to 3.1x increase in performance. The I/O time for the sequential was 736.834 sec. while the I/O for the concurrent one was 151.435 sec. However, invert which was supposedly to technically be the fastest didn’t get a higher increase(4.9X increase) in performance than smear(5.56x increase). 

2) The Concurrent times are way faster than the sequential version. This is because of the use of multiple cores to do more work at one time. In the Sequential version you was essentially doing all the work core which made it take more time doing one thing more that the other. The Concurrent one you could have someone reading in images while the other thread was writing to disk. If it was supposed to be longer, the only reason I could think of is that we are working with a queue of a fixed size and if the queue was full then we would have to wait and block, which would lead to more time. 


