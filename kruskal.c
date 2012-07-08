#include<stdio.h>
#include<stdlib.h>
#define MAX 9

int ne=1,n,i,j,cost[MAX][MAX],v,u,a,b,parent[MAX],min,mincost=0;
int find(int );
int uni(int , int );

int main()
{
	printf("enter the no of vetices: ");
	scanf("%d", &n);
	printf("enter the cost\n");
	for(i=1;i<=n;i++)
		for(j=1;j<=n;j++) {
			scanf("%d", &cost[i][j]);
			if(cost[i][j]==0)
				cost[i][j]=999;
		}
	printf("\nThe edges of Minimum Cost Spanning Tree are\n\n");
	while(ne<n)
	{
		for(i=1,min=999;i<=n;i++)
			for(j=1;j<=n;j++)
				if(cost[i][j]<min) {
					min=cost[i][j];
					a=u=i;
					b=v=j;
				}
		u=find(u);
		v=find(v);
		if(uni(u,v))
		{
			printf("\n%dedge(%d,%d)cost=%d\n",ne++,a,b,min);
			mincost+=min;
		}
		cost[a][b]=cost[b][a]=999;
	}
	printf("Minimum cost=%d\n", mincost);
	return 0;
}


int find(int i)
{
	while( parent[i])
		i=parent[i];
	return (i);
}

int uni(int i, int j)
{
	if(i!=j)
	{
		parent[j]=i;
		return 1;
	}
	return 0;
}
