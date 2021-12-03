class Firewall:
    day = 13
    test = 1

    def process(self, raw_input):
        firewall = self.parseInput(raw_input)
        start = 0
        while True:
            severity = self.simulatePass(firewall, start)
            if start == 0:
                severityOn0Start = severity
            if severity == 0:
                break
            start += 1

        return {'severityOn0Start': severityOn0Start, 'delay': start}

    def simulatePass(self, firewall, start):
        severity = 0
        for depth, rangeAtDepth in firewall.items():
            real_depth = depth + start
            cycle = rangeAtDepth * 2 - 2
            pos = real_depth % cycle
            if pos >= rangeAtDepth:
                pos = rangeAtDepth - 2 - (pos % rangeAtDepth)
            if pos == 0:
                if start != 0:
                    return -1
                severity += real_depth * rangeAtDepth
        return severity

    def parseInput(self, raw_input):
        result = {}
        for row in raw_input:
            row_elements = row.split(':')
            key = int(row_elements[0])
            value = int(row_elements[1])
            result[key] = value
        return result

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
    exercise = Firewall()
    exercise.executeTestOnFile(exercise.get_input_filename())
