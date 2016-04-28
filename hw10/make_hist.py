from matplotlib import pyplot as plt
f = open('AsData.txt').readlines()

degree = list()
count = list()

for e in f:
    deg, c = e.split('\t')
    degree.append(int(deg))
    count.append(int(c.strip('\n')))

sorted_list = sorted(list(zip(degree, count)), key=lambda x: x[0])

plt.hist([x[0] for x in sorted_list], weights=[x[1] for x in sorted_list], bins=150)
plt.xlabel('Degree')
plt.ylabel('Count')
plt.title('Histogram of ASes and their Degrees')
plt.show()
