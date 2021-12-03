class Memory:
    currentState = []
    history = list()

    def __init__(self, memory) -> None:
        super().__init__()
        self.currentState = list(memory)
        self.history = list()
        hash_value = hash(tuple(self.currentState))
        self.history.append(hash_value)

    def execute(self):
        start = self.currentState.index(max(self.currentState))
        stop = start + self.currentState[start]
        self.currentState[start] = 0
        for pos in range(start + 1, stop + 1):
            self.currentState[pos % len(self.currentState)] += 1
        hash_value = hash(tuple(self.currentState))
        if hash_value in self.history:
            return len(self.history) - self.history.index(hash_value)
        else:
            self.history.append(hash_value)
            return False


class Memories:
    day = 6
    test = 2

    def process(self, raw_input):
        memory = Memory(self.parseInput(raw_input))
        count = 0
        while True:
            count += 1
            loopsize = memory.execute()
            if loopsize is not False:
                break
        if self.test == 1:
            return count
        else:
            return loopsize

    def parseInput(self, raw_input):
        return [[int(block) for block in row.split()] for row in raw_input][0]

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
    exercise = Memories()
    exercise.executeTestOnFile(exercise.get_input_filename())
