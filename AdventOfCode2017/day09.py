from anytree import Node, LevelOrderIter


class Stream:
    day = 9
    test = 1

    def process(self, raw_input):
        raw_row = self.parseInput(raw_input)
        escaped_input = self.escapeInput(raw_row)
        root = self.buildTree(escaped_input)
        score = 0
        count = 0
        garbage_count = 0
        for node in LevelOrderIter(root):
            if node.garbage:
                garbage_count += len(node.garbage_content)
            else:
                count+=1
                if node.parent:
                    node.score = node.parent.score + 1
                else:
                    node.score = 1
                score += node.score
        return {'count': count, 'score': score, 'garbage': garbage_count}


    def parseInput(self, raw_input):
        return [row for row in raw_input][0]

    def escapeInput(self, raw_row):
        result = []
        escape = False
        for character in raw_row:
            if escape:
                escape = False
                continue
            if character == '!':
                escape = True
                continue
            result.append(character)
        return result

    def buildTree(self, escaped_input):
        level = 0
        count = 0
        return self.buildNode(escaped_input, None, level, count)

    def buildNode(self, input, parent, level, count):
        node = Node((level, count))
        node.garbage = input.pop(0) == '<'
        node.garbage_content = []
        if parent:
            node.parent = parent
        child_count = 0
        while len(input) > 0:
            next = input[0]
            if (node.garbage and next == '>') or (not node.garbage and next == '}'):
                input.pop(0)
                count += 1
                if len(input) > 0 and input[0] == ',':
                    input.pop(0)
                    sister = self.buildNode(input, parent, level, count)
                return node
            elif node.garbage:
                node.garbage_content.append(input.pop(0))
            elif next in ['<', '{']:
                child = self.buildNode(input, node, level+1, child_count)
                child_count += 1
            else:
                raise ValueError("I dont know what to do with: " + next)

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
    exercise = Stream()
    exercise.executeTestOnFile(exercise.get_input_filename())
