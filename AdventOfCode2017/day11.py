from collections import Counter
from functools import reduce
from itertools import cycle, islice

# Inspiration: https://www.redblobgames.com/grids/hexagons/

class Hex:
    day = 11
    test = 1

    def process(self, raw_input):
        path = self.parseInput(raw_input)
        x = 0
        y = 0
        z = 0
        max_distance = 0
        for step in path:
            if step == 'n':
                y += 1
                z -= 1
            if step == 'ne':
                x += 1
                z -= 1
            if step == 'se':
                x += 1
                y -= 1
            if step == 's':
                z += 1
                y -= 1
            if step == 'sw':
                z += 1
                x -= 1
            if step == 'nw':
                y += 1
                x -= 1
            max_distance = max(max_distance, self.hexDistanceFromCenter(x, y, z))

        return {'distance': self.hexDistanceFromCenter(x, y, z), 'max_distance': max_distance}

    def hexDistanceFromCenter(self, x, y, z):
        return max(abs(x),abs(y),abs(z))

    def parseInput(self, raw_input):
        return [row.split(',') for row in raw_input][0]

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
    exercise = Hex()
    exercise.executeTestOnFile(exercise.get_input_filename())
