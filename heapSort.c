#include<stdio.h>

void restoreHup(int *a, int i)
{
	int v=a[i];
	while((i>1)&&(a[i/2]<v))
	{
		a[i]=a[i/2];
		i=i/2;
	}
	a[i]=v;
}

void restoreHdown(int *a, int i, int n)
{
	int v=a[i];
	int j=i*2;
	while(j<=n)
	{
		if((j<n)&&(a[j]<a[j+1]))
			j++;
		if(a[j]<a[j/2])
			break;
		a[j/2]=a[j];
		j=j*2;
	}
	a[j/2]=v;
}

void main()
{
	int a[20], temp, i, j, n;
	printf("Enter the number of ele: ");
	scanf("%d", &n);
	printf("Enter the ele:\n");
	for(i=1;i<=n;i++)
	{
		scanf("%d", &a[i]);
		restoreHup(a, i);
	}
	for(i=1, j=n; i<=j; i++)
	{
		temp=a[1];
		a[1]=a[n];
		a[n]=temp;
		n--;
		restoreHdown(a, 1, n);
	}
	printf("Numbers after sorting:\n");
	for(i=1;i<=j;i++)
	{
		printf("%4d", a[i]);
	}
	printf("\n");
}
	
