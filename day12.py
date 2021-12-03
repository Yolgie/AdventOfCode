from itertools import repeat

from networkx import Graph, connected_components


class Pipes:
    day = 12
    test = 1

    def process(self, raw_input):
        pipes = self.parseInput(raw_input)
        pipeGraph = Graph()
        for name, connections in pipes:
            pipeGraph.add_node(name, connections=connections)
            pipeGraph.add_edges_from(zip(connections, repeat(name)))
            test = 1
        connected = list(connected_components(pipeGraph))
        set_with_zero = filter(lambda set: 0 in set, connected).__next__()
        return {'groupWithZero':len(set_with_zero), 'groupCount':len(connected)}

    def parseInput(self, raw_input):
        return [self.parseRow(row) for row in raw_input]

    def parseRow(self, raw_row):
        row_elements = raw_row.split()
        name = int(row_elements.pop(0))
        pipe = row_elements.pop(0)
        targets = [int(target) for target in ''.join(row_elements).split(',')]
        return (name, targets)

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
    exercise = Pipes()
    exercise.executeTestOnFile(exercise.get_input_filename())
