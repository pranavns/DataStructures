#include<stdio.h>
#define MAX 20

int findpivot(int *a, int i, int j)
{
	int fir=a[i], k;
	for(k=i+1;k<=j;k++)
	{
		if(a[k]>fir)
			return(k);
		else if(a[k]<fir)
			return(i);
	}
	return (-1);
}

int part(int *a, int i, int j, int p)
{
	int temp;
	int l=i,r=j;
	do {
		temp=a[l];
		a[l]=a[r];
		a[r]=temp;
		while(a[l]<p)
			l++;
		while(a[r]>=p)
			r--;
	} while(l<=r);
	return (l);
}
		
void qsort(int *a, int i, int j)
{
	int k, pivot, pindex;
	pindex=findpivot(a, i, j);
	if(pindex!=-1)
	{
		pivot=a[pindex];
		k = part(a, i, j, pivot);
		qsort(a, i, k-1);
		qsort(a, k, j);
	}
}	

void main()
{
	int n, i, a[MAX];
	printf("Enter the no of elements: ");
	scanf("%d", &n);
	printf("Enter the elements\n");
	for(i=0;i<n;i++)
		scanf("%d", &a[i]);
	qsort(a, 0, n-1);
	printf("after sorting the elements\n");
	for(i=0;i<n;i++)
		printf("%d\t", a[i]);
	printf("\n");
}
