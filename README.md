Simulated-computer
==================

Simulating a basic computer using java. This includes a CPU, shared memory, a scheduler and tasks. Tasks do "nothing" 
for a bit then perform a system call asking for IO. IO devices create an IO event every so often. The CPU also handles context switching, and perform commands on tasks. 
