
#include <stdio.h>
#include <stdlib.h>

#include "splash.h"
#include "probe.h"

int main (int argc, char *argv[])
{
	if (argc != 2) {
		printf("Usuage:\n\tprobe dumpfile");
		exit(1);
	}
	buildSplashtable(argv[1]);


}
