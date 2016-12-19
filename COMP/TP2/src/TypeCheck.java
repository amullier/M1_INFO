/**
 * Type checking operations (NOTE: this class must be implemented by the
 * student; the methods indicated here can be seen as suggestions; note that
 * some minor checks can still be performed directly in VSLTreeParser.g).
 * 
 */
public class TypeCheck {

	/**
	 * Vérification de type pour les opérations binaires
	 */
	public static Type checkBinOp(Type t1, Type t2) {
		if (t1 == Type.INT && t2 == Type.INT){
			return Type.INT;
		}
		else if (t1 == Type.I_CONST && t2 == Type.I_CONST){
			return Type.I_CONST;
		}
		else if (t1 == Type.INT && t2 == Type.I_CONST){
			return Type.INT;
		}
		else if (t1 == Type.I_CONST && t2 == Type.INT){
			return Type.INT;
		}
		else {
			return Type.ERROR;
		}
	}

}
