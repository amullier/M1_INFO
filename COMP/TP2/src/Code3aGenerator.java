/**
 * Classe statique contenant les méthodes de génération
 * de Code3a
 */
public class Code3aGenerator {

	/**
	 * Génère le Code 3a pour l'instruction :  VAR t
	 * @param Operand3a t : Variable a déclarer
	 * @return Code3a
	 */
	public static Code3a genVar(Operand3a t) {
		return new Code3a(new Inst3a(Inst3a.TAC.VAR, t, null, null));
	}

	/**
	 * Génère le Code 3a pour la déclaration d'une variable
	 * @param var : Variable déclarée
	 * @return Code3a
	 */
	public static Code3a genDecl(VarSymbol var){
		return new Code3a(new Inst3a(Inst3a.TAC.VAR, (Operand3a)var, null, null));
	}

	/**
	 * Génère le Code 3a pour l'opération binaire
	 * @param op : Inst3a.TAC.XXX
	 * @param temp : Variable temporaire dans laquelle sera stockée le résultat
	 * @param exp1 : premier opérande 
	 * @param exp2 : second opérande 
	 * @return Code3a
	 */
	public static Code3a genBinOp(Inst3a.TAC op, Operand3a temp, ExpAttribute exp1,ExpAttribute exp2) {
		Inst3a i = new Inst3a(op, temp, exp1.place, exp2.place);
		Code3a code = exp1.code;
		code.append(exp2.code);
		code.append(genVar(temp));
		code.append(i);
		return code;
	}

	/**
	 * Génère le Code 3a pour l'opération unaire (- unaire)
	 * @param op : Inst3a.TAC.XXX
	 * @param temp : Variable temporaire dans laquelle sera stockée le résultat
	 * @param exp1 : premier opérande 
	 * @return Code3a
	 */
	public static Code3a genUnaireOp(Inst3a.TAC op, Operand3a temp, ExpAttribute exp1) {
		Code3a code = exp1.code;
		code.append(genVar(temp));
		Inst3a i = new Inst3a(op, temp, exp1.place, null);
		code.append(i);
		return code;
	}

	/**
	 * Génère le Code 3a pour l'affectation
	 * @param var : Variable affecté
	 * @param exp : Expression a affecté à var
	 * @return Code3a
	 */
	public static Code3a genAff(Operand3a var, ExpAttribute exp) {
		Code3a code = new Code3a();
		code.append(exp.code);
		Inst3a i = new Inst3a(Inst3a.TAC.COPY, var, exp.place, null);
		code.append(i);
		return code;
	}

	/************************************************************\
	 *                                                          *
	 *                     PRINT / READ                         *
	 *                                                          *
	\************************************************************/

	/**
	 * Génère le code pour afficher un String
	 * @param msg : String à afficher
	 * @return Code3a
	 */
	public static Code3a genAffichageString(String msg){
		Code3a code = new Code3a();

		//On enlève les guillemets du msg
		msg = msg.replaceAll("\"","");

		Data3a data = new Data3a(msg);
		code.appendData(data);
		
		//Ajout de l'argument data pour l'appel de fonction
		code.append(new Inst3a(Inst3a.TAC.ARG, data.getLabel(), null, null));

		//Ajout de l'appel système
		code.append(new Inst3a(Inst3a.TAC.CALL, null, SymbDistrib.builtinPrintS, null));
		return code;
	}

	/**
	 * Génère le code pour afficher un entier 
	 * @param exp : Expression a afficher
	 * @return Code3a
	 */
	public static Code3a genAffichageExpression(ExpAttribute exp) {
		Code3a code = new Code3a();
		code.append(exp.code);

		//Ajout de l'argument data pour l'appel de fonction
		code.append(new Inst3a(Inst3a.TAC.ARG, exp.place, null, null));

		//Ajout de l'appel système
		code.append(new Inst3a(Inst3a.TAC.CALL, null, SymbDistrib.builtinPrintN, null));
		return code;
	}

	/**
	 * Génère le code pour une lecture d'entier
	 * @param var : Variable dans laquelle sera stockée le retour de résultat
	 * @return Code3a
	 */
	public static Code3a genLectureEntier(VarSymbol var) {
		Code3a code = new Code3a();
		code.append(new Inst3a(Inst3a.TAC.CALL, var, SymbDistrib.builtinRead, null));
		return code;
	}

	/************************************************************\
	 *                                                          *
	 *                  IF x THEN ... (ELSE ...)                *
	 *                                                          *
	\************************************************************/

	/**
	 * Génère le code pour le IF sans ELSE
	 * @param cond : Condition pour le if
	 * @param then_block_code : Code du corps du IF
	 * @return Code3a
	 */
	public static Code3a genIfThen(ExpAttribute cond, Code3a then_block_code){
		Code3a code = new Code3a();
		
		//On crée un label de fin pour savoir où aller si l'expression n'est pas vérifiée
		LabelSymbol label_fin = SymbDistrib.newLabel();
		
		//Test de l'expression si elle n'est pas vérifiée GOTO label_fin
		code.append(new Inst3a(Inst3a.TAC.IFZ, cond.place, label_fin, null));

		//Block d'instructions du IF
		code.append(then_block_code);

		//Ajout du label de fin du IF
		code.append(new Inst3a(Inst3a.TAC.LABEL, label_fin, null, null));

		return code;
	}

	/**
	 * Génère le code pour IF avec ELSE
	 * @param cond : Condition pour le if
	 * @param then_block_code : Code du corps du IF
	 * @param else_block_code : Code du corps du ELSE
	 * @return Code3a
	 */
	public static Code3a genIfThenElse(ExpAttribute cond, Code3a then_block_code , Code3a else_block_code){
		Code3a code = new Code3a();

		//On crée un label label_fin_si pour savoir où aller si l'expression n'est pas vérifiée
		LabelSymbol label_fin_si = SymbDistrib.newLabel();

		//On crée un label label_fin_else pour savoir où aller à la fin du si
		LabelSymbol label_fin_else = SymbDistrib.newLabel();

		//Test de l'expression si elle n'est pas vérifiée GOTO label_fin_si
		code.append(new Inst3a(Inst3a.TAC.IFZ, cond.place, label_fin_si, null));

		//Block d'instructions du IF
		code.append(then_block_code);

		//GOTO label_fin_else : Quand on execute le 1er block on n'execute pas le 2e block
		code.append(new Inst3a(Inst3a.TAC.GOTO, label_fin_else, null, null));

		//Ajout du label de fin du IF
		code.append(new Inst3a(Inst3a.TAC.LABEL, label_fin_si, null, null));

		//Block d'instructions du ELSE
		code.append(else_block_code);

		//Ajout du label de fin du ELSE
		code.append(new Inst3a(Inst3a.TAC.LABEL, label_fin_else, null, null));

		return code;
	}


	/************************************************************\
	 *                                                          *
	 *                         WHILE                            *
	 *                                                          *
	\************************************************************/

	/**
	 * Génère le code associé à l'instrcution WHILE
	 *
	 * @param cond : ExpAttribute de l'expression qui gère la condition
	 * @param while_block_code : Block d'instructions 3a dans le corps du while
	 * @return Code3a
	 */
	public static Code3a genWhile(ExpAttribute cond, Code3a while_block_code){
		Code3a code = new Code3a();

		//On crée un label label_debut pour savoir où reprendre pour ré-éxécuter un passage de boucle while
		LabelSymbol label_debut_while = SymbDistrib.newLabel();

		//On crée un label label_fin_while pour savoir où aller si l'expression n'est pas vérifiée
		LabelSymbol label_fin_while = SymbDistrib.newLabel();

		//Ajout du label de debut du WHILE (avant le test de l'expression)
		code.append(new Inst3a(Inst3a.TAC.LABEL, label_debut_while, null, null));

		code.append(cond.code);

		//Test de l'expression si elle n'est pas vérifiée GOTO label_fin_while
		code.append(new Inst3a(Inst3a.TAC.IFZ, cond.place, label_fin_while, null));

		//Block d'instructions du WHILE
		code.append(while_block_code);

		//On retourne au block de début du WHILE pour re-tester	la condition
		code.append(new Inst3a(Inst3a.TAC.GOTO, label_debut_while, null, null));

		//Ajout du label de fin du WHILE
		code.append(new Inst3a(Inst3a.TAC.LABEL, label_fin_while, null, null));

		return code;
	}


	/************************************************************\
	 *                                                          *
	 *                        TABLEAUX                          *
	 *                                                          *
	\************************************************************/

	/**
	 * Génére le code pour un élément de tableau
	 * @param var_temporaire : Variable temporaire dans laquelle sera stockée la valeur du tableau
	 * @param tab : Tableau
	 * @param indice : Expression qui correspond à l'indice de la case du tableau
	 * @return Code3a
	 */
	public static Code3a genTabElem(Operand3a var_temporaire, Operand3a tab , ExpAttribute indice) {
		Code3a code = new Code3a();
		code.append(genVar(var_temporaire));
		code.append(new Inst3a(Inst3a.TAC.TABVAR, var_temporaire , tab,indice.place));
		return code;
	}

	/**
	 * Génère le code pour l'affectation dans une case d'un tableau
	 * ex : tab[indice] = x + 2
	 *
	 * @param var_temporaire
	 * @param tab : IDENT du tableau
	 * @param indice : Expression de l'indice de la case du tableau
	 * @param exp : Expression à affecter dans la case du tableau
	 * @return Code3a
	 */
	public static Code3a genTabAff(Operand3a tab , ExpAttribute indice,Operand3a exp){
		Code3a code = new Code3a();
		code.append(new Inst3a(Inst3a.TAC.VARTAB, tab ,indice.place, exp));
		return code;		
	}

	/**
	 * Génère le code pour la lecture d'un entier à affecter dans une case de tableau
	 * @param t : Ident du tableau
	 * @param indice : Expression de l'indice de la case du tableau
	 * @return Code3a
	 */
	public static Code3a genTabAffLecture(Operand3a tab , ExpAttribute indice){
		Code3a code = new Code3a();

		//Création d'une variable temporaire
		VarSymbol var_temporaire = SymbDistrib.newTemp();
		code.append(genVar(var_temporaire));

		//On place la lecture dans la variable temporaire
		code.append(new Inst3a(Inst3a.TAC.CALL, var_temporaire, SymbDistrib.builtinRead,null));

		//On affecte la variable temporaire à la case du tableau
		code.append(new Inst3a(Inst3a.TAC.VARTAB, tab ,indice.place, var_temporaire));

		return code;
	}

	/************************************************************\
	 *                                                          *
	 *                       FONCTIONS                          *
	 *                                                          *
	\************************************************************/
	/**
	 * Génère le code d'une fonction
	 * @param functionSymbol : désigne la fonction à générer
	 * @param paramsCode : Code des paramètres de la fonction
	 * @param corpsCode : Code du corps de la fonction
	 * @return Code3a
	 */
	public static Code3a genFonction(FunctionSymbol functionSymbol, Code3a paramsCode, Code3a corpsCode){
		Code3a code = new Code3a();

		//On génère le label : label nom_fonction
		code.append(new Inst3a(Inst3a.TAC.LABEL, functionSymbol, null, null));

		//On ajoute l'instruction de début de fonction BEGINFUNC
		code.append(new Inst3a(Inst3a.TAC.BEGINFUNC, null, null, null));

		//On ajoute le code des paramètres
		code.append(paramsCode);

		//On ajoute le corps de la fonction
		code.append(corpsCode);

		//On ajoute l'instruction de fin de fonction ENDFUNC
		code.append(new Inst3a(Inst3a.TAC.ENDFUNC, null, null, null));

		return code;
	}

	/**
	 * Génère le code d'un retour de fonction
	 * @param return_exp : Expression de retour de fonction
	 * @return Code3a
	 */
	public static Code3a genRetourFonction(ExpAttribute return_exp){
		Code3a code = new Code3a();

		//On récupère le code de l'expression à retourner
		code.append(return_exp.code);
		code.append(new Inst3a(Inst3a.TAC.RETURN, return_exp.place, null, null));

		return code;
	}

	/**
	 * Génère le code d'un argument
	 * Exemple : ARG i
	 * @param e : Expression désignant l'argument à passer à une fonction
	 * @return Code3a
	 */
	public static Code3a genArgument(ExpAttribute e) {

		Code3a code = e.code;
       	code.append(new Inst3a(Inst3a.TAC.ARG, e.place, null, null));
 		return code;
	}


	/**
	 * Génère le code pour un appel de procédure : Fonction sans retour de résultat
	 * @param nom_fonction : ident de ma fonction
	 * @param arg_code : Code généré par la lecture des arguments
	 * @return Code3a
	 */
	public static Code3a genAppelProcedure(String nom_fonction, Code3a arg_code) {

		Code3a code = new Code3a();

		//on génère le code des arguments
		code.append(arg_code);

		//on génère le code pour l'appel de la procédure
		code.append(new Inst3a(Inst3a.TAC.CALL, null, new LabelSymbol(nom_fonction), null));

		return code;
	}

	/**
	 * Génère le code pour un appel de fonction
	 * @param nom_fonction : ident de ma fonction
	 * @param type_fonction : type de ma fonction
	 * @param arg_code : Code généré par la lecture des arguments
	 * @return ExpAttribute : 
	 */
	public static ExpAttribute genAppelFonction(String nom_fonction, FunctionType type_fonction, Code3a arg_code) {

		Code3a exp_code = new Code3a();

		//On crée une variable temporaire
		VarSymbol var_resultat = SymbDistrib.newTemp();

		exp_code.append(genVar(var_resultat));
		//On ajoute le code pour les arguments
		exp_code.append(arg_code);

		//On ajoute le code de l'appel de fonction
		exp_code.append(new Inst3a(Inst3a.TAC.CALL, var_resultat, new LabelSymbol(nom_fonction), null));

		//On crée un attribut expression
		return new ExpAttribute(type_fonction.getReturnType(), exp_code, var_resultat);
	}


}