package eu.stamp_project.mutationtest.descartes.operators;

import java.lang.annotation.Retention;

import org.pitest.reloc.asm.MethodVisitor;
import org.pitest.reloc.asm.Opcodes;
import org.pitest.reloc.asm.Type;
import org.pitest.reloc.asm.commons.Method;

/**
 * Replaces the method body with a <code>return null</code> statement
 */
public class NullMutationOperator extends MutationOperator{

    /**
     * Returns a value indicating whether the operator can transform the given method.
     * In this case whether <code>null</code> could be assigned to the return type.
     *
     * @param method Method to be tested by the operator
     * @return A boolean value indicating if null can be assigned to the return type
     */
    @Override
    public boolean canMutate(Method method) {
        int target = method.getReturnType().getSort();
        return target == Type.OBJECT || target == Type.ARRAY;
    }

    @Override
    public boolean canReturnSelfObject(Method method) {
        int target = method.getReturnType().getSort();
        if (target == Type.OBJECT)
            return true;
        if (target == Type.ARRAY)
            return false;
        return false;
    }
    @Override
    public void generateCode(Method method, MethodVisitor mv) {
        assert canMutate(method);
        if(canReturnSelfObject(method))
            mv.visitVarInsn(Opcodes.ALOAD, 0);
        else
            mv.visitInsn(Opcodes.ACONST_NULL);
        mv.visitInsn(Opcodes.ARETURN);
    }

    @Override
    public String getID() {
        return "null";
    }

    @Override
    public String getDescription(){
        return "All method instructions replaced by: return null;";
    }

}
