def map(f, R):
    return [t for (k,v) in R for t in f(k,v)]

def reduce(f, R):
    keys = {k for (k,v) in R}
    return [f(k1, [v for (k2,v) in R if k1 == k2]) for k1 in keys]

likes = [
    ('classical music', [1,2,3,4,5]),
    ('sports', [1,4,5,6]),
    ('tv', [1,3,2]),
    ('movies', [1,2])
]


intersection_map = map(lambda k,v: [((p1, p2), k) for p1 in v for p2 in v if p1 < p2], likes)
intersection = reduce(lambda k, vs: (k,vs), intersection_map)

union1 = map(lambda k,v: [('*', (p, k)) for p in v], likes)
red_union_1 = reduce(lambda k, vs: (k, vs), union1)
#print(red_union_1)

cross = map(lambda k, v: [((p1[0], p2[0]), [p1[1], p2[1]]) for p1 in v for p2 in v if p1[0] < p2[0]], red_union_1)
red_cross = reduce(lambda k,vs: (k, list(set([v for f in vs for v in f]))), cross)

both = red_cross + intersection

red_both = reduce(lambda k, vs: (k, sorted(vs, key=lambda x: len(x))), both)
red_coef = reduce(lambda k, vs: (k, 0 if len(vs[0]) == 1 else len(vs[0][0]) / len(vs[0][1])), red_both)
for i in red_coef:
    print(i)
