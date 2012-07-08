#include<stdio.h>

int a,b, i,j,cost[10][10],ne=1,n,min,mincost=0;
int visit[10]={0};

void main()
{
printf("Enter the number of nodes: ");
scanf("%d", &n);
printf("Cost\n");
for(i=1;i<n;i++)
	for(j=1;j<n;j++) {
		scanf("%d", &cost[i][j]);
		if(cost[i][j]==0)
			cost[i][j]=999;
		}
visit[1]=1;
while(ne<n)
{
	for(i=1,min=999;i<n;i++)
		for(j=1;j<n;j++)
			if((cost[i][j]<min)&&(visit!=0))
			{
				min=cost[i][j];
				a=i;b=j;
			}
	if(visit[a]==0||visit[b]==0)
	{
	printf("\n%dedge(%d,%d)cost=%d\n", ne++, a, b, min);
	mincost+=min;
	visit[b]=1;	
	}
	cost[a][b]=cost[b][a]=999;
}
printf("minimum cost: %d\n", mincost);
}
