import networkx as nx

fin = open("input.txt", "r")
lines = fin.readlines()

g = nx.Graph()

for line in lines:
	a, xb = line.split(': ')
	bs = set(map(str.strip, xb.split()))
	a = a.strip()

	for b in bs:
		g.add_edge(a, b)

cuts = nx.minimum_edge_cut(g)

for a, b in cuts:
	g.remove_edge(a, b)

bigSegment = max(nx.connected_components(g), key=len)
print((len(g)-len(bigSegment))*len(bigSegment))