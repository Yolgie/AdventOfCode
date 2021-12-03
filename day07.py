from collections import Counter

from anytree import Node, PostOrderIter


class Circus:
    day = 7
    test = 2

    def process(self, raw_input):
        nodes = {new_node.name: new_node for new_node in self.parseInput(raw_input)}
        for node in nodes.values():
            if len(node.childrenNames) > 0:
                children = [nodes.get(childrenName) for childrenName in node.childrenNames]
                node.children = children
        root = nodes.popitem()[1].root

        if self.test == 2:
            for node in PostOrderIter(root):
                if len(node.children) == 0:
                    node.towerWeight = node.weight
                else:
                    childrenWeights = [child.towerWeight for child in node.children]
                    if len(set(childrenWeights)) > 1:
                        weight_count = Counter(childrenWeights)
                        right_weight = weight_count.most_common(1)[0][0]
                        wrong_child = [child for child in node.children if child.towerWeight != right_weight][0]
                        return wrong_child.weight + (right_weight - wrong_child.towerWeight)
                    else:
                        node.towerWeight = node.weight + sum(childrenWeights)

        # print(root.parent)
        # print(root.children)
        # print(RenderTree(root))
        return root.name

    def parseInput(self, raw_input):
        return [self.makeNode(row) for row in raw_input]

    def makeNode(self, row):
        data = row.split()
        name = data[0]
        weight = int(data[1].strip('()'))  # remove braces
        children = []
        if len(data) > 1:
            children = [name.strip(',') for name in data[3:]]  # remove commas
        return Node(name, weight=weight, childrenNames=children)

    def executeTestOnFile(self, input_filename):
        with open(input_filename) as input_file:
            raw_input = input_file.readlines()

        result = self.process(raw_input)
        print(result)

        with open(self.get_output_filename(), 'w') as output_file:
            output_file.write(str(result))

    def get_output_filename(self):
        return "day" + str(self.day).zfill(2) + "." + str(self.test).zfill(2) + ".output"

    def get_input_filename(self):
        return "day" + str(self.day).zfill(2) + ".input"


if __name__ == "__main__":
    exercise = Circus()
    exercise.executeTestOnFile(exercise.get_input_filename())
