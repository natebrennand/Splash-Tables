
from sys import argv, exit
from math import sqrt

PRINT_FORMAT = 'MIN: {}\nMAX: {}\nAVG: {}\nVARIANCE: {}\nSTD DEV: {}'

def average(l):
    return 1.0 * sum(l) / len(l)

def get_stats(stat_file):
    stats = []
    with open(stat_file) as f:
        f.readline()
        for line in f:
            stats.append(float(line))

    mn = min(stats)
    mx = max(stats)
    avg = average(stats)
    var = average(map(lambda x: (x - avg)**2, stats))
    std_dev = sqrt(var)

    print PRINT_FORMAT.format(mn, mx, avg, var, std_dev)

if __name__ == '__main__':
    if len(argv) != 2:
        print 'USAGE: python stat.py <stat filename>'
        exit(1)
    get_stats(argv[1])
