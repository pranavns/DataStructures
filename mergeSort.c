#include<stdio.h>

void msort(int a[], int low, int high)
{
	int pivot, length=high-low+1, merge1, merge2, i;
	int working[length];
	if(low==high)
		return;
	pivot=(low+high)/2;
	msort(a, low, pivot);
	msort(a, pivot+1, high);
	for(i=0;i<length;i++)
       		working[i]=a[low+i];
	merge1=0;
	merge2=pivot-low+1;
	for(i=0;i<length;i++)
       	{
		if(merge2<=high-low)
			if(merge1<=pivot-low)
				if(working[merge1]>working[merge2])
					a[i+low]=working[merge2++];
				else
					a[i+low]=working[merge1++];
			else
				a[i+low]=working[merge2++];
		else
			a[i+low]=working[merge1++];
	}
}

void main()
{
	int n, i, a[20];
	printf("Enter the no of ele: ");
	scanf("%d", &n);
	printf("enter the elements\n");
	for(i=0;i<n;i++)
		scanf("%d", &a[i]);
	msort(a, 0, n-1);
	printf("after sorting the elements\n");
	for(i=0;i<n;i++)
		printf("%4d", a[i]);
	printf("\n");
}
