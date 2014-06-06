
# Splash Table

- Lisa Li
- Nate Brennand


Splash tables are a hashtable variant designed with CPU optimizations in mind.
In this project we created a full featured Java implementation as well as a CPU optimized C probe.


## Java Implementation

The Splash table is CPU conscious in its probing reoutine by avoiding all non constant-size loops as well as not using `if` statements.
This reduces the opportunities for the CPU to come to a branch in instructions and potentially waste cycles.


## C Probe

The C probe uses a technique outlined in [Efficent Hash Probes on Modern Processors](/papers/efficient_hash_probes_on_modern_processors.pdf) which utilizes direct CPU instructions to compare bucket values with no CPU instruction branching.



