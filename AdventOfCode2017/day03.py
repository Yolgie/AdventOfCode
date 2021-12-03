import itertools

import math


class Grid:
    grid = {}
    last = None

    def __init__(self) -> None:
        super().__init__()
        self.set(0, 0, 1)
        self.last = 0

    def set(self, x, y, value):
        self.grid[(x, y)] = value

    def calculatePosition(self, x, y):
        if (x, y) in self.grid:
            return self.grid[(x, y)]
        sumOfNeighbors = 0
        neighbors = itertools.product([x - 1, x, x + 1], [y - 1, y, y + 1])
        for coordinate in neighbors:
            if coordinate in self.grid:
                sumOfNeighbors += self.grid[coordinate]
        self.set(x, y, sumOfNeighbors)
        return sumOfNeighbors

    def calculateCount(self, count):
        return self.calculatePosition(*calculateSpiralPosition(count))

    def calculateNext(self):
        self.last = self.last + 1
        return self.calculateCount(self.last)


def calculateSpiralPosition(n):
    k = math.ceil((math.sqrt(n) - 1) / 2)
    t = 2 * k + 1
    m = t * t
    t = t - 1
    if n >= m - t:
        return (k - (m - n), -k)
    else:
        m = m - t
    if n >= m - t:
        return (-k, -k + (m - n))
    else:
        m = m - t
    if n >= m - t:
        return (-k + (m - n), k)
    else:
        return (k, k - (m - n - t))


def calculateManhattanDistance(x, y):
    return abs(x) + abs(y)


class SpiralMemory:
    day = 3
    test = 2

    def process(self, raw_input):
        count = self.parseInput(raw_input)
        if self.test == 1:
            position = calculateSpiralPosition(count)
            result = calculateManhattanDistance(*position)
        if self.test == 2:
            result = self.calculateFirstNumberLargerThan(count)
        return result

    def calculateFirstNumberLargerThan(self, target):
        sum = 0
        grid = Grid()
        while sum <= target:
            sum = grid.calculateNext()
        return sum

    def parseInput(self, raw_input):
        for row in raw_input:
            return int(row)

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
    spiralMemory = SpiralMemory()
    spiralMemory.executeTestOnFile(spiralMemory.get_input_filename())
