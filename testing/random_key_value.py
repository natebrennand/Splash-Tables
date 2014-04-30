
from sys import argv, exit
from math import pow
from random import randint

MAX_INT = pow(2, 31)
USED_KEY = set()

def get_2_rand_int():
    k = randint(1, MAX_INT)
    v = randint(1, MAX_INT)
    while k in USED_KEY:
        k = randint(0, MAX_INT)
    USED_KEY.add(k)
    return k, v


def output_pairs(n):
    for _ in range(int(pow(2, n))):
        k, v = get_2_rand_int()
        print "{} {}".format(*get_2_rand_int())


if __name__ == '__main__':
    if len(argv) != 2:
        print 'You must pass in the power of 2 your table contains!'
        print 'USUGE: python {} <num>'.format(argv[0])
        exit(1)

    output_pairs(int(argv[1]))
