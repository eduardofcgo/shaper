# Shaper

Shaper produces automatic designs for modular houses[1]. 
It follows a Genetic Algorithm approach. 
A set of architectural design rules and 
placement constraints are incorporated 
into the GA fitness function,
efficiently producing several layout
solutions.

<p align="center">
    <img src="https://github.com/eduardofcgo/shaper/blob/main/solutions.png?raw=1" />
</p>

A layout is defined as a rectangle grid,
with a predetermined set of rooms with fixed
dimensions and placement restrictions.
Rooms are rectangles and must fulfill the 
design rules without rotating.
The encoded representation of the placement 
of each room is akin to a gene. 
A set of these encoded placements represents 
the complete layout of the house or the chromosome. 

This Genetic Algorithm implementation
concerns only a single individual,
as opposed to a population. The first
iteration of the search starts with a
randomly generated chromosome. The following
generation is produced uniquely by mutating
the previous one. The goal is to arrive
at a solution which complies with all 
the restrictions and placement constraints;
solutions that do not respect this are defined
as candidate solutions. Such candidate layouts
often display rooms which are overlapping or even
placed out of the bounds of the grid.

An example of a placement restriction could be
“Room A should be at the back of the house”,
or even “Room A and B should be next to each other”.
For each placement restriction, an adjustment
consisting in a two dimension translation vector
is computed, such that it's application to the room
would allow it to comply with the rule. In GA terms,
these will be considered mutations.
The  application of these mutations will not
necessarily yield a solution because each mutation
concerns only a single room and its placement restriction - 
not the overall correctness of the layout.

Since this set of mutations M is a direct consequence
of the noncompliance of the individual with the rules,
the fitness of the chromosome can be described to be
inversely proportional to the sum of their magnitude.

<p align="center">
    <img src="https://github.com/eduardofcgo/shaper/blob/main/fitness.svg?raw=1" />
</p>

Each generation, the individual will be subjected
to the mutations M. In order to avoid local optimum,
random mutations responsible for introducing
genetic diversity are employed as well.
For each mutation generated, the probability
of discarding it for a new random mutation follows 
an Adaptive Genetic Algorithm (AGA) aproach[2].
This subjects high fitness solutions to lower levels of 
random mutation and the converse to sub-average solutions. 
This promotes the intended broader exploration of the 
search space, whilst maintaining a high conversion
rate towards global optima. In this AGA,
we define the probability of random mutation as follows.

<p align="center">
    <img src="https://github.com/eduardofcgo/shaper/blob/main/prm.svg?raw=1" />
</p>

While a random search might take more than 30 minutes (2015 Macbook Pro),
this GA is able to generate several solutions in under a minute
for the following layout.

1. The vestibule must be placed next to the garage;
2. The toilet and technical room must be placed next to the
   garage;
3. The kitchen must be placed at the front of the house;
4. The dining room must be placed next to the kitchen;
5. The living room must be placed next to the dining room;
6. The single bedroom must be placed next to another single
   bedroom or a bathroom;
7. The bathroom is placed next to single bedroom;
8. The double bedroom must be placed at the back;

## References

1. de Almeida, A., Taborda, B., Santos, F., Kwiecinski, K., & Eloy, S. (2016, October). A genetic algorithm application for automatic layout design of modular residential homes. In 2016 IEEE International Conference on Systems, Man, and Cybernetics (SMC) (pp. 002774-002778).
2. Srinivas, Maloth & Patnaik, Lalit. (1994). Patnaik, L.M.: Adaptive probabilities of crossover and mutation in genetic algorithms. IEEE Trans. Syst. Man Cybern. 24, 656-667. Systems, Man and Cybernetics, IEEE Transactions on. 24. 656 - 667. 10.1109/21.286385. 
