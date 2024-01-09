import java.util.HashMap;

public class AssemblyProgram {
        HashMap<String,Integer> registers = new HashMap<>();
        boolean flag = true;
        public void executeProgram(Instructions instruction){
            if(flag){
                switch(instruction.operation){
                    case MV -> mvInstructions(instruction.operands);
                    case ADD -> addInstructions(instruction.operands);
                    case SHOW -> showInstructions(instruction.operands);
                    case IFGT -> ifGreaterThan(instruction.operands);
                }
            }
            else{
                System.out.println("Can't perform the Operation");
            }   
        }
        public void endIfFunction(){
            this.flag=true;
        }
        private void ifGreaterThan(String[] operands) {
            String reg=operands[0];
            int value_for_comparison = Integer.parseInt(operands[1]);
            if(registers.get(reg) < value_for_comparison){
                this.flag = false;
            }
        }
        private void mvInstructions(String[] operands){
            String reg= operands[0];
            int value = Integer.parseInt(operands[1].substring(1));
            if(!registers.containsKey(reg)){
                registers.put(reg,value);
            }
            else{
                System.out.println("This register already exists");
            }
        }
        private void addInstructions(String[] operands) {
            String reg = operands[0];
            if(!registers.containsKey(reg)){
                System.out.println("This register "+reg+" doesn't exist");
            }
            else {
                int value = 0;
                if (!registers.containsKey(operands[1])) {
                    value = registers.get(reg) + Integer.parseInt(operands[1]);
                } else {
                    value = registers.get(reg) + registers.get(operands[1]);
                }
                registers.put(reg, value);
            }
        }
        private void showInstructions(String[] operands) {
            String reg = operands[0];
            if(registers.containsKey(reg)&& flag)
                System.out.println(reg + ":" + registers.get(reg));
            else
                System.out.println("This register doesn't exist");
        }
    }
