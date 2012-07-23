/*	a sample program demonstating the working of fork 
	this program is used to execute shell commands 
	from the commandline parsing it by space and
	executing in a specfic interval of time */

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>

int forkExe(char *);	//function to execute a single process(here command)

int main(int argc, char *argv[])
{
	int i;
	if(argc == 1)
	{
		printf("\nusage is : \t%s <command_1> <command_2> ... <command_n>\n\n", argv[0]);
		return(-1);
	}
	for(i = 1; i < argc; i++)	//executing each command in the argument list
		forkExe(argv[i]);

  	return 0;
}

int forkExe(char *comnd)
{
	int status;
	pid_t pid = fork();	//process id forked with unique id
	if(pid == 0)
	{
		printf("\nChild Process begins with id %d\n", getpid());	//for identification
		sleep(2);
		system(comnd);  // execute the command
		exit(EXIT_SUCCESS);	//when system complete this chid process then its exit status is returned
	}
  
	else if(pid < 0)		// if The fork failed
	{
		printf("Failed to fork(): %s\n", comnd);
		status = -1;
	}
	printf("\nParent Process Started with id %d\n", getpid()); //for identification ;any work to do with parent
	sleep(2);
	return status;
}

/*--------------------------------OUTPUT--------------------------------------
guest@pranav-Aspire-5253:~/DataStructures$ gcc forkProgram.c 
guest@pranav-Aspire-5253:~/DataStructures$ ./a.out who ps date ls

Parent Process Started with id 3281

Child Process begins with id 3282

Parent Process Started with id 3281

Child Process begins with id 3283
guest    tty7         2012-07-23 12:09 (:0)
guest    pts/0        2012-07-23 12:38 (:0.0)
guest    pts/1        2012-07-23 13:18 (:0.0)
guest    pts/2        2012-07-23 13:20 (:0.0)

Parent Process Started with id 3281

Child Process begins with id 3286
  PID TTY          TIME CMD
 3176 pts/2    00:00:00 bash
 3281 pts/2    00:00:00 a.out
 3282 pts/2    00:00:00 a.out <defunct>
 3283 pts/2    00:00:00 a.out
 3286 pts/2    00:00:00 a.out
 3287 pts/2    00:00:00 sh
 3288 pts/2    00:00:00 ps

Parent Process Started with id 3281

Child Process begins with id 3289
Mon Jul 23 13:23:24 IST 2012
guest@pranav-Aspire-5253:~/DataStructures$ a.out		       btreeAho$Node.class  openhashing.c
binarySearchTree.c     bTree.java	    permut_nonrecursive.c
BinaryTree.class       dijsktra.c	    ppLab
BinaryTree.java        forkProgram.c	    prims.c
BinaryTree$Node.class  heapSort.c	    quicksort.c
btreeAho.class	       kruskal.c	    quickSort.c
btreeAho.java	       mergeSort.c

guest@pranav-Aspire-5253:~/DataStructures$ 
------------------------------------------------------------------------------*/
