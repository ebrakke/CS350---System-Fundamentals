from copy import deepcopy
G = {'A','a','b','c','d','e','f','g','B'}

E = {('A','a'): 1000,('a','b'):10,('b','c'):100,('c','d'):100,('d','B'):500,('a','c'):50,('a','e'):100,('e','c'):100,('e','f'):50,('f','g'):50,('g','d'):50}
P = [['A','a','b','c','d','B'],['A','a','c','d','B'],['A','a','e','c','d','B'],['A','a','e','f','g','d','B']]


def max_flow(e,paths):
	flow = 0
	c_paths = deepcopy(paths)
	ce = deepcopy(e)
	while True:
		for p in c_paths:
			cap = min([ce[u,v] for u,v in zip(p[:-1], p[1:])])
			if cap == 0:
				c_paths.remove(p)
				continue
			flow += cap
			for u,v in zip(p[:-1], p[1:]):
				ce[u,v] = ce[u,v] - cap
		if len(c_paths) == 0:
			break
	print flow

def bottleneck(p, e):
	return min([[(u,v),e[u,v]] for u,v in zip(p[:-1], p[1:])], key=lambda x: x[1])

def upgrade(e,paths):
	c = max_flow(e, paths)
	best_upgrade = (None, None)
	for p in paths:
		edge,cap = bottleneck(p, e)
		while True:
			e[edge] = cap + 1
			cp = max_flow(e, paths)
			if cp >  c:
				c = cp
				best_upgrade = (edge, cap)
			else:
				break
	return c, best_upgrade
		
		
		