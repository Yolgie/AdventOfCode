class Jumps:
    day = 5
    test = 2

    def process(self, raw_input):
        jumps = self.parseInput(raw_input)
        count = 0
        pos = 0
        while pos >= 0 and pos < len(jumps):
            newPos = pos + jumps[pos]
            if self.test == 1:
                jumps[pos] += 1
            elif self.test == 2:
                if jumps[pos] >= 3:
                    jumps[pos] -= 1
                else:
                    jumps[pos] += 1
            count += 1
            pos = newPos
        return count

    def parseInput(self, raw_input):
        return [int(row) for row in raw_input]

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
    jumps = Jumps()
    jumps.executeTestOnFile(jumps.get_input_filename())
