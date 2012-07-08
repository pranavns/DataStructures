/*program implementing openhash datastructure*/

#include<stdio.h>
#include<malloc.h>
#define HASH_TABLE_SIZE 10

struct node
{
	int data;
	struct node *next;
};
struct node *bucketHeader[HASH_TABLE_SIZE];

typedef struct node N;
int key,item;
void insert (void);
void display(void);
short member (int);
void delete (void);

int main()
{
	int x, ch;
	do {
		printf("\n\tMAIN MENU(OPEN HASH)\n");
		printf("1.INSERT\n2.DISPLAY\n3.SEARCH\n4.DELETE\n5.EXIT\n");
		printf("Enter your choice: ");
		scanf("%d", &ch);
		switch (ch)
		{
			case 1:  insert();  break;
			case 2:  display(); break;
			case 3:  printf("Enter the item to be search: ");
				 scanf("%d", &x);
				 if(member(x)){printf("Item found!\n");}
				 else {printf("Item not found!\n");}  break;
			case 4:  delete();  break;
			case 5:  return 0;
			default: printf("Invalid option!!!\n");
		}
	} while(1);
}		

void insert()
{
	N *loc, *p;
	p 	= (N *)malloc(sizeof(N));
	loc	= (N *)malloc(sizeof(N));
	printf("Enter the item to be inserted: ");
	scanf("%d", &item);
	p -> data = item;
	p -> next = NULL;
	key = item % HASH_TABLE_SIZE;
	loc = bucketHeader[key];
	if (loc == NULL)
		bucketHeader[key] = p;
	else {
		while (loc->next != NULL)
			loc = loc -> next;
		loc->next = p;
	}
}

void display()
{
	N *p;
	p 	= (N *)malloc(sizeof(N));
	printf("Hash table is:\n");
	for(key=0;key<HASH_TABLE_SIZE;key++)
	{
		if(bucketHeader[key] == NULL)
			printf("---");
		else {
			p=bucketHeader[key]; 
			while(p != NULL )
				{printf("-->%d ", p -> data);
				p=p->next;}
		}
		printf("\n");
	}
}

short member(int x)
{
	N *loc;
	key = x % HASH_TABLE_SIZE;
	loc = (N*)malloc(sizeof(N));
	loc = bucketHeader[key];
	while( loc != NULL )
	{
		if (loc -> data == x)
			{return(1);}
		loc = loc -> next;
	}
	return(0);
}

void delete()
{
	N *loc, *start;
	printf("Enter the item to be deleted: ");
	scanf("%d", &item);
	key = item % HASH_TABLE_SIZE;
	loc = (N*)malloc(sizeof(N));
	loc = bucketHeader[key];
	if(!member(item))
		{printf("Item doesn't exist!\n");
		return;}
	else if(loc -> data == item)
		{
		bucketHeader[key] = loc -> next;
		free(loc);
		printf("Element deleted!\n");
		return;}
	else {
		start = (N*)malloc(sizeof(N));
		while( loc != NULL )
		{
			if (loc -> data == item)
				{start->next  = loc -> next;
				free(loc);
				printf("Element deleted!\n");
				return;}
			start = loc;
			loc = loc -> next;
		}
	
	}
}
