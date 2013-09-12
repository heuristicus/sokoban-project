Board representation
=============

- [ ] Read in map from stdin
- [ ] Get positions of player, goals and blocks
- [ ] 2 level representation? static things and moveable things separate
- [ ] apply action to the map and return the resulting map
- [ ] function to print the map

Pathfinding
=============

- [ ] Take two positions on the map and find the path between them
- [ ] Take player position and find path to all of the positions which are neighbours of blocks
- [ ] Search algorithm choice (BFS or A*?)

** Extras
- Dijkstra's algorithm implementation so that path calculation is efficient? Only recalculate path cost for nodes which are children of the blocked node.
