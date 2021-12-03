class RegisterCommands:
    comparators = {
        '==': lambda a, b: a == b,
        '!=': lambda a, b: a != b,
        '<=': lambda a, b: a <= b,
        '>=': lambda a, b: a >= b,
        '<': lambda a, b: a < b,
        '>': lambda a, b: a > b
    }

    def __init__(self, raw_input) -> None:
        super().__init__()
        list_input = raw_input.split()
        self.target = list_input[0]
        self.sign = 1 if list_input[1] == 'inc' else -1
        self.amount = self.sign * int(list_input[2])
        self.condition_target = list_input[4]
        self.condition_comparator = list_input[5]
        self.condition_value = int(list_input[6])

    def execute(self, registers):
        if self.target not in registers:
            registers[self.target] = 0
        if self.condition_target not in registers:
            registers[self.condition_target] = 0

        condition_values = (registers.get(self.condition_target), self.condition_value)
        condition_lambda = self.comparators.get(self.condition_comparator)
        if (condition_lambda(*condition_values)):
            registers[self.target] = registers.get(self.target) + self.amount
            return registers[self.target]


class Register:
    day = 8
    test = 2

    def process(self, raw_input):
        registerCommands = self.parseInput(raw_input)
        register = {}
        max_value = 0
        for registerCommand in registerCommands:
            new_value = registerCommand.execute(register)
            if new_value:
                max_value = max(max_value, new_value)

        if self.test == 1:
            return max(register.values())
        else:
            return max_value

    def parseInput(self, raw_input):
        return [RegisterCommands(row) for row in raw_input]

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
    exercise = Register()
    exercise.executeTestOnFile(exercise.get_input_filename())
