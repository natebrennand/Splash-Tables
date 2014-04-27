#include <stdio.h>
#include "splash.h"

int main (int argc, char *argv[])
{
	if (argc != 2)
	{
		printf( "Error: arguments are incorrect");
	}
	else {
		splashtable(argv[1]);
	}
}
