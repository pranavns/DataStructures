#include<stdio.h>
#include<string.h>
#define MAX 20

/*factorial calculation funtion*/
int fact(int num)
{
	if ( num == 1 ) return 1;
 	return( num * fact( num-1 ));
}

/*funtion to print reverse of a string*/
void reverse(char *a, int len)
{
	int i;
 	for( i=len-1 ;i>=0; i-- )
    		printf("%c", a[i]);
 	printf("\n");
}

/*swapping two characters in a string*/
void exchangerev(int m, int n, char *a, int len)
{
	char temp;
 	temp=a[m];
 	a[m]=a[n];
 	a[n]=temp;
 	printf("%s\n", a);	//print the string after exchanging
 	reverse(a, len);	//print the reverse of string
}

/*triggering funtion for listing all permutations*/
void perm(int fac, char *a, int len)
{
	int i, j, half_fact=fac/2;
	printf("%s\n", a);			//print initial string before exchanging
 	reverse(a, len);			//print its reverse
 	for( j=0, i=2 ;i<=half_fact; i++, j++)
	 {
   		if(j==len-1)			//checks end of string
		 {
			exchangerev(0, len-1, a, len);
			i++; j=0;
    		 }
   		exchangerev(j, j+1, a, len);	//exchanging two consecutive characters
   	 }
}

int main(int argc, char *argv[])
{
	char *str;
	if (argc != 2)
		return 1;
	str = argv[1]; 	//commandline input of string
	if( strlen( str ) == 1 )
		printf("Only one permutation:%s\n", str);
	else
	 {
		printf("The permutations are:\n");
		perm( fact( strlen( str ) ) , str, strlen( str ) );
	 }
	return 0;
}
