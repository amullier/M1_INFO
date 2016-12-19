tree grammar VSLTreeParser;

options {
	language     = Java;
	tokenVocab   = VSLParser;
	ASTLabelType = CommonTree;
	output=AST;
}

/**
 * Axiome de la grammaire
 */
s [SymbolTable symTab] returns [Code3a code]: 
	program[symTab]
		{
			$code = $program.code;
		}
;

/**
 * Programme
 */
program [SymbolTable symTab] returns [Code3a code]:
	 ^(PROG  
			{ Code3a program_code = new Code3a(); }  
			(
				unit[symTab] 
				{ 
					//On concatène le code de chaque unit
					program_code.append($unit.code); 
				} 
			)+  
			{ $code = program_code; } 
		)
;


unit [SymbolTable symTab] returns [Code3a code]:
	function[symTab]
		{
			//On propage l'attribut code
			$code = $function.code;
		}

	| proto[symTab]
;

/**
 * Déclaration de fonction
 */
function [SymbolTable symTab] returns [Code3a code]:
	^(FUNC_KW type IDENT  
		{ 
			//On crée un type de fonction que l'on complètera pendant le parcours des param
			FunctionType functionType = new FunctionType($type.type,false);  //false -> fonction
			$symTab.enterScope();
		}  
		
		param_list[symTab, functionType] ^(BODY statement[symTab])
			{
				//On récupère l'ident de la fonction
				Operand3a ident_fonction = $symTab.lookup($IDENT.text);

				//Si l'ident est déjà défini et que le type n'est pas compatible on signale une erreur
				if ((ident_fonction != null) && (!functionType.isCompatible(ident_fonction.type))){
					Errors.redefinedIdentifier($IDENT, $IDENT.text, "L'ident est déjà déclaré lors de la déclaration de la fonction");
				}

				//On crée un label et un FunctionSymbol pour la fonction
				LabelSymbol functionLabel = new LabelSymbol($IDENT.text);
				FunctionSymbol functionSymbol = new FunctionSymbol(functionLabel, functionType);

				$symTab.leaveScope();
				//On ajoute la fonction dans la table des symboles
				$symTab.insert($IDENT.text, functionSymbol);

				//On génère le code de la fonction
				$code = Code3aGenerator.genFonction(functionSymbol, $param_list.code, $statement.code);
			}
	)
;

/**
 * Déclaration de prototype
 */
proto [SymbolTable symTab]:
	^(PROTO_KW type IDENT  
		{ 
			//On crée un type de fonction que l'on complètera pendant le parcours des param
			FunctionType functionType = new FunctionType($type.type, true); //true -> prototype
			$symTab.enterScope();
		}  

		param_list[symTab, functionType]
			{
				//On récupère l'ident du prototype
				Operand3a ident_prototype = $symTab.lookup($IDENT.text);

				//Si l'ident est déjà défini on signale une erreur
				if (ident_prototype != null){
					Errors.redefinedIdentifier($IDENT, $IDENT.text, "L'ident est déjà déclaré lors de la déclaration du prototype");
				}

				//On crée un label et un FunctionSymbol pour le prototype
				LabelSymbol functionLabel = new LabelSymbol($IDENT.text);
				FunctionSymbol functionSymbol = new FunctionSymbol(functionLabel, functionType);

				$symTab.leaveScope();

				//On ajoute la fonction dans la table des symboles
				$symTab.insert($IDENT.text, functionSymbol);
			}
	) 	
;


type returns [Type type]:
	INT_KW
		{
			$type = Type.INT;
		}
	| VOID_KW
		{
			$type = Type.VOID;
		}
;

/**
 * Liste de paramètres de fonction
 */
param_list [SymbolTable symTab, FunctionType functionType] returns [Code3a code]:
	^(PARAM  
		{ 
			Code3a c = new Code3a(); 
		}  
		( 
			param[symTab, functionType] 
				{ 
					c.append($param.code); 
				} 
		)*  
		{ 
			$code = c; 
		}
	)

;

/**
 * Paramètre de fonction
 */
param [SymbolTable symTab, FunctionType functionType] returns [Code3a code]:
	^(ARRAY IDENT)
		{
			//On cherche l'ident dans la table des symboles
			Operand3a search_ident = $symTab.lookup($IDENT.text);

			//Si l'ident est présent : ERROR 
			if(search_ident!=null){
				Errors.redefinedIdentifier($IDENT, $IDENT.text, "L'ident de tableau a déjà été déclaré.");
			}

			//Si l'ident n'est pas déjà déclaré : on crée une nouvelle variable
 			VarSymbol paramArray = new VarSymbol((ArrayType)search_ident.type, $IDENT.text, $symTab.getScope());  

 			paramArray.setParam();

 			//On ajoute la variable dans la table des symboles
			$symTab.insert($IDENT.text, paramArray);

			functionType.extend(search_ident.type);

			$code = Code3aGenerator.genDecl(paramArray);
		}
	| IDENT
		{
			//On ajoute le paramètre du prototype ou de la fonction a la table des symboles
			VarSymbol param = new VarSymbol(Type.INT, $IDENT.text, $symTab.getScope());

			//On précise qu'il s'agit d'un paramètre
			param.setParam();

			$symTab.insert($IDENT.text, param);

			//On ajoute le type du paramètre à la liste des types des arguments de la fonction
			functionType.extend(Type.INT);

			//On génère une variable
			$code = Code3aGenerator.genVar(param);
		}
;

/**
 * Instructions
 */
statement [SymbolTable symTab] returns [Code3a code]:
	
	//Gestion de l'affectation
	^(ASSIGN_KW expression[symTab]
		( 
			//Avec une variable
			IDENT
		 		{
					//On récupère la variable dans la table des symboles
					Operand3a id_var = $symTab.lookup($IDENT.text);

		 			//Si elle est nulle : ERROR
					if(id_var==null){
						Errors.unknownIdentifier($ASSIGN_KW, $IDENT.text, "AFFECTATION : L'identificateur n'est pas déclaré.");
					}
		 			
		 			//Vérification de type
		 			Type ty = TypeCheck.checkBinOp(id_var.type, $expression.expAtt.type);

		 			//Si les types ne sont pas compatibles : ERROR
		 			if (ty == Type.ERROR){
		 				Errors.incompatibleTypes($ASSIGN_KW, id_var.type, ty, "AFFECTATION : Les types sont incompatibles.");
		 			}

					//On génère le code associé à l'affectation
					$code = Code3aGenerator.genAff(id_var, $expression.expAtt);
		 		}
			//Avec un tableau
			| 
				{
					//On récupère la place de l'expression a affecter
					Operand3a exp_place = $expression.expAtt.place;
				}
				array_elem_affectation[symTab,exp_place]
				{
					//Affectation une expression dans une case de tableau
					Code3a c = new Code3a();
					c.append($expression.expAtt.code);
					c.append($array_elem_affectation.code);
					$code = c;
				}
			
		)
	)

	//Retour de résultat
	| ^(RETURN_KW expression[symTab])
		{
			//Code généré pour le retour de résultat
			$code = Code3aGenerator.genRetourFonction($expression.expAtt);
		}

	//Affichage d'une printlist
	| ^(PRINT_KW print_list[symTab])
		{
			//On retourne le code généré par print_list
			$code = $print_list.code;
		}

	//Lecture d'une read_list
	| ^(READ_KW read_list[symTab])
		{
			//On retourne le code généré par read_list
			$code = $read_list.code;
		}
	//SI expression ALORS st1 (SINON st2)?
	| ^(IF_KW expression[symTab]  st1=statement[symTab] (st2=statement[symTab])? )
		{			
			//On différencie le cas du if simple du if/then/else
			if (st2 == null){
				//If/Then
				$code = Code3aGenerator.genIfThen($expression.expAtt, $st1.code);
			}
			else{
				//If/Then/Else
				$code = Code3aGenerator.genIfThenElse($expression.expAtt, $st1.code, $st2.code);
			}
		}

	//WHILE
	| ^(WHILE_KW expression[symTab] st=statement[symTab])
		{
			$code = Code3aGenerator.genWhile($expression.expAtt,$st.code);
		}

	//Appel de procédure  : Pas d'affectation
	| ^(FCALL_S IDENT { FunctionType expectedType = new FunctionType(Type.INT); }  argument_list[symTab, expectedType]?)
		{
			//On vérifie que l'ident est déjà présent dans la table des symboles
			Operand3a ident_fonction = $symTab.lookup($IDENT.text);

			if (ident_fonction == null){
				Errors.unknownIdentifier($IDENT, $IDENT.text, "La fonction appelée n'est pas déclarée");
			}
				
			//On regarde si l'ident appelé correspond bien à une FunctionSymbol
			if (!(ident_fonction instanceof FunctionSymbol)){
				Errors.unknownIdentifier($IDENT, $IDENT.text, "La fonction appelée n'est pas déclarée en tant que fonction");
			}

			$code = Code3aGenerator.genAppelProcedure($IDENT.text, $argument_list.code);
		}

	//Block
	| 
		{
			$symTab.enterScope();
		}
		block[symTab]
		{
			//On retourne le code généré pour le block d'instruction
			$code = $block.code;

			//On quitte le scope du block
			$symTab.leaveScope();
		}
;

array_elem_affectation[SymbolTable symTab, Operand3a exp_place] returns [Code3a code]:
	^(ARELEM IDENT expression[symTab])
	{
		Operand3a ident_tab = $symTab.lookup($IDENT.text);

		//On vérifie que l'ident est déclaré dans la table des symboles
		if (ident_tab == null) {
			Errors.unknownIdentifier($IDENT, $IDENT.text, "Identificateur de tableau non déclaré");
		}

		//On vérifie que l'ident est bien un pointeur
		// if(!(ident_tab.type.isCompatible(Type.POINTER))){
		// 	Errors.incompatibleTypes($IDENT, Type.POINTER, ident_tab.type, null);
		// }

		//On vérifie que l'expression est bien un indice
		if(!($expression.expAtt.type.isCompatible(Type.INT))){
			Errors.incompatibleTypes($ARELEM, Type.INT, $expression.expAtt.type, null);	
		}

		Code3a c = new Code3a();
		c.append($expression.expAtt.code);

		c.append(Code3aGenerator.genTabAff(ident_tab,$expression.expAtt,exp_place));

		$code= c;
	}
;

/**
 * Block d'instructions
 */
block [SymbolTable symTab] returns [Code3a code]:
	//Block d'instructions avec déclarations
	^(BLOCK declaration[symTab] inst_list[symTab])
		{
			Code3a block_code = new Code3a();

			//On ajoute le code généré pour les déclarations
			block_code.append($declaration.code);

			//On ajout le code généré pour la suite d'instructions
			block_code.append($inst_list.code);

			$code = block_code;
		}

	//Block d'instructions sans déclarations
	| ^(BLOCK inst_list[symTab])
		{
			//Le code généré est réduit au code la suite d'instructions
			$code = $inst_list.code;
		}
;

/**
 * Suite d'instructions
 */
inst_list [SymbolTable symTab] returns [Code3a code]:
	^(INST 
		{
			Code3a inst_list_code = new Code3a();
		}
		(
			statement[symTab] 
			{
				//On concatène le code généré de chaque instruction
				inst_list_code.append($statement.code);
			}
		)+
		{
			$code = inst_list_code;
		}
	)
;

	
/**
 * Gestion des expressions
 */
expression [SymbolTable symTab] returns [ExpAttribute expAtt]:
	
	//Addition de deux expressions
	^(PLUS e1=expression[symTab] e2=expression[symTab]) 
		{ 
			//On regarde si le type est correct
			Type ty = TypeCheck.checkBinOp($e1.expAtt.type, $e2.expAtt.type);
			if(ty == Type.ERROR){
				Errors.incompatibleTypes($PLUS, Type.INT, ty, null);
			}

			//On génère une nouvelle variable
			VarSymbol temp = SymbDistrib.newTemp();
			
			//Génération du Code3a
			Code3a cod = Code3aGenerator.genBinOp(Inst3a.TAC.ADD, temp, $e1.expAtt, $e2.expAtt);
			$expAtt = new ExpAttribute(ty, cod, temp);
		}

	//Soustraction de deux expressions
	| ^(MINUS e1=expression[symTab] e2=expression[symTab]) 
		{ 
			//On regarde si le type est correct
			Type ty = TypeCheck.checkBinOp($e1.expAtt.type, $e2.expAtt.type);
			if(ty == Type.ERROR){
				Errors.incompatibleTypes($MINUS, Type.INT, ty, null);
			}

			//On génère une nouvelle variable
			VarSymbol temp = SymbDistrib.newTemp();
			
			//Génération du Code3a
			Code3a cod = Code3aGenerator.genBinOp(Inst3a.TAC.SUB, temp, $e1.expAtt, $e2.expAtt);
			$expAtt = new ExpAttribute(ty, cod, temp);
		}

	//L'expression se réduit à une expression primaire ou est composée d'une expression composée de mult/div.
	|  f=factor[symTab] 
		{ 
			$expAtt = $f.expAtt; 
		}
;

/**
 * Gestion des expressions contenant des multiplications/divisions
 * -> permet de gérer la priorité
 */
factor [SymbolTable symTab] returns [ExpAttribute expAtt]:
	 ^(MUL e1=expression[symTab] e2=expression[symTab])
		{
			//On regarde si le type est correct
			Type ty = TypeCheck.checkBinOp($e1.expAtt.type, $e2.expAtt.type);
			if(ty == Type.ERROR){
				Errors.incompatibleTypes($MUL, Type.INT, ty, null);
			}

			//On génère une nouvelle variable
			VarSymbol temp = SymbDistrib.newTemp();

			//Génération du Code3a
			Code3a cod = Code3aGenerator.genBinOp(Inst3a.TAC.MUL, temp, $e1.expAtt, $e2.expAtt);
			$expAtt = new ExpAttribute(ty, cod, temp);
		}
	
	| ^(DIV e1=expression[symTab] e2=expression[symTab])
		{
			//On regarde si le type est correct
			Type ty = TypeCheck.checkBinOp($e1.expAtt.type, $e2.expAtt.type);
			if(ty == Type.ERROR){
				Errors.incompatibleTypes($DIV, Type.INT, ty, null);
			}

			//On génère une nouvelle variable
			VarSymbol temp = SymbDistrib.newTemp();

			//Génération du Code3a
			Code3a cod = Code3aGenerator.genBinOp(Inst3a.TAC.DIV, temp, $e1.expAtt, $e2.expAtt);
			$expAtt = new ExpAttribute(ty, cod, temp);
		}

	| pe=primary[symTab] 
		{ 
			$expAtt = $pe.expAtt; 
		}
;

/**
 *	Expression réduite sans opérateurs
 */
primary [SymbolTable symTab] returns [ExpAttribute expAtt]: 
	//Opération du moins unaire
	^(NEGAT p=primary[symTab])
		{
			//On crée une nouvelle variable
			VarSymbol temp = SymbDistrib.newTemp();

			//Génération du Code3a avec le générateur d'opérations unaire
			$expAtt = new ExpAttribute($p.expAtt.type, Code3aGenerator.genUnaireOp(Inst3a.TAC.NEG, temp, $p.expAtt), temp);
		}

	//Nombre entier
	| INTEGER
		{
			ConstSymbol cs = new ConstSymbol(Integer.parseInt($INTEGER.text));
			$expAtt = new ExpAttribute(Type.INT, new Code3a(), cs);
		}

	//Variable
	| IDENT
		{
			VarSymbol ident_var = (VarSymbol) $symTab.lookup($IDENT.text);

			//On vérifie que l'ident est déclaré dans la table des symboles
			if (ident_var == null) {
				Errors.unknownIdentifier($IDENT, $IDENT.text, null);
			}
			$expAtt = new ExpAttribute( ((Operand3a) ident_var).type, new Code3a(), ((Operand3a) ident_var));
		}

	//Element d'un tableau
	| 
		{
			// On crée une nouvelle variable temporaire
			VarSymbol temp = SymbDistrib.newTemp();
		}
		array_elem_lecture[symTab,temp]
		{
			//TODO : Type int ? choix judicieux ?
			//On retourne l'expAtt généré par array_elem_lecture
			$expAtt = new ExpAttribute(Type.INT,$array_elem_lecture.code,temp);
		}

	//Appel de fonction
	| ^(FCALL IDENT  { FunctionType expectedType = new FunctionType(Type.INT); }  argument_list[symTab, expectedType]?
		{
			//On regarde si la fonction est définie dans la table des symboles
			Operand3a ident_fonction = $symTab.lookup($IDENT.text);
			if (ident_fonction == null){
				Errors.unknownIdentifier($IDENT, $IDENT.text, "L'identifiant de fonction n'est pas défini au moment de l'appel");
			}

			//On regarde s'il s'agit bien d'une fonction
			if (!(ident_fonction instanceof FunctionSymbol)){
				Errors.unknownIdentifier($IDENT, $IDENT.text, "La fonction appelée n'est pas déclarée en tant que fonction");
			}

			//On retourne l'expression associée à l'appel de fonction
			$expAtt = Code3aGenerator.genAppelFonction($IDENT.text, (FunctionType)ident_fonction.type, $argument_list.code);
		}
		)
	//Expression avec des parenthèses
	| LP! e=expression[symTab] RP!
		{
			$expAtt = $e.expAtt;
		}
;


/**
 * Element d'un tableau en lecture : IDENT[expression]
 */
array_elem_lecture[SymbolTable symTab,VarSymbol var_temp] returns [Code3a code]:
	^(ARELEM IDENT expression[symTab])
	{
		Operand3a ident_tab = $symTab.lookup($IDENT.text);

		//On vérifie que l'ident est déclaré dans la table des symboles
		if (ident_tab == null) {
			Errors.unknownIdentifier($IDENT, $IDENT.text, "Identificateur de tableau non déclaré");
		}

		//On vérifie que l'ident est bien un pointeur
		// if(!(ident_tab.type.isCompatible(Type.POINTER))){
		// 	Errors.incompatibleTypes($IDENT, Type.POINTER, ident_tab.type, null);
		// }

		//On vérifie que l'expression est bien un indice
		if(!($expression.expAtt.type.isCompatible(Type.INT))){
			Errors.incompatibleTypes($ARELEM, Type.INT, $expression.expAtt.type, null);	
		} 

		Code3a c = new Code3a();
		c.append($expression.expAtt.code);
		c.append(Code3aGenerator.genTabElem(var_temp,ident_tab,$expression.expAtt));
		$code = c;
	}
;


argument_list [SymbolTable symTab, FunctionType expectedType] returns [Code3a code]:
	{
		Code3a c = new Code3a(); 
	}  
	( 
		expression[symTab]  
		{ 
			c.append(Code3aGenerator.genArgument($expression.expAtt)); 
			expectedType.extend($expression.expAtt.type); 
		}  
	)+  
	{ 
		$code = c; 
	}
;

/**
 * Liste de print_item : concaténation de code
 */
print_list [SymbolTable symTab] returns [Code3a code]:
	{ Code3a print_list_code = new Code3a(); }
	(
		print_item[symTab] 
		{
			print_list_code.append($print_item.code);
		}
	)+
	{ $code = print_list_code; }
;

/**
 * Affichage d'un message ou d'une expression
 */
print_item [SymbolTable symTab] returns [Code3a code]:
	//Text à afficher
	TEXT
		{
			//On génère le code pour afficher un string
		   $code = Code3aGenerator.genAffichageString($TEXT.text);
		}
	//Expression
	| expression[symTab]
		{
			//On génère le code pour afficher une expression
		   	$code = Code3aGenerator.genAffichageExpression($expression.expAtt);
		}
;

/**
 * Liste de read_item : concaténation de code des read_item
 */
read_list [SymbolTable symTab] returns [Code3a code]:
	{ Code3a read_list_code = new Code3a(); }
	
	( 
		read_item[symTab]
		{
			read_list_code.append($read_item.code);
		}
	)+ 

	{ $code = read_list_code; }
;


read_item [SymbolTable symTab] returns [Code3a code]:
	IDENT
		{
			Operand3a ident_var = $symTab.lookup($IDENT.text);
			
			//On vérifie que l'ident est déclaré dans la table des symboles
			if (ident_var == null) {
				Errors.unknownIdentifier($IDENT, $IDENT.text, null);
			}

			//On génère le code de la lecture
			$code = Code3aGenerator.genLectureEntier((VarSymbol)ident_var);
		}
	|
		array_elem_aff_lecture[symTab]
		{
			//On génère le code de la lecture
			$code = $array_elem_aff_lecture.code;
		}
;
array_elem_aff_lecture[SymbolTable symTab] returns [Code3a code]:
	^(ARELEM IDENT expression[symTab])
	{
		Operand3a ident_tab = $symTab.lookup($IDENT.text);

		//On vérifie que l'ident est déclaré dans la table des symboles
		if (ident_tab == null) {
			Errors.unknownIdentifier($IDENT, $IDENT.text, "Identificateur de tableau non déclaré");
		}

		//On vérifie que l'ident est bien un pointeur
		// if(!(ident_tab.type.isCompatible(Type.POINTER))){
		// 	Errors.incompatibleTypes($IDENT, Type.POINTER, ident_tab.type, null);
		// }

		//On vérifie que l'expression est bien un indice
		if(!($expression.expAtt.type.isCompatible(Type.INT))){
			Errors.incompatibleTypes($ARELEM, Type.INT, $expression.expAtt.type, null);	
		}

		$code= Code3aGenerator.genTabAffLecture(ident_tab, $expression.expAtt);
	}

;
declaration [SymbolTable symTab] returns [Code3a code]:
	^(DECL
		{ Code3a declaration_code = new Code3a(); }
		( 
			decl_item[symTab]
			{
				//On concatène le code de toutes les déclarations
				declaration_code.append($decl_item.code);
			}
		)+
		{ $code = declaration_code; }
	)
;
decl_item [SymbolTable symTab] returns [Code3a code]:
	IDENT
		{
			//On cherche l'ident dans la table des symboles
			Operand3a search_ident = $symTab.lookup($IDENT.text);

			//Si l'ident est présent : ERROR 
			if(search_ident!=null){
				Errors.redefinedIdentifier($IDENT, $IDENT.text, "L'ident a déjà été déclaré.");
			}

			//Si l'ident n'est pas déjà déclaré : on crée une nouvelle variable
 			VarSymbol declarationVar = new VarSymbol(Type.INT, $IDENT.text, $symTab.getScope());  

 			//On ajoute la variable dans la table des symboles
			$symTab.insert($IDENT.text, declarationVar);

			$code = Code3aGenerator.genDecl(declarationVar);
		}
	| ^(ARDECL IDENT INTEGER)
		{
			//On cherche l'ident dans la table des symboles
			Operand3a search_ident = $symTab.lookup($IDENT.text);

			//Si l'ident est présent : ERROR 
			if(search_ident!=null){
				Errors.redefinedIdentifier($IDENT, $IDENT.text, "L'ident de tableau a déjà été déclaré.");
			}

			//Si l'ident n'est pas déjà déclaré : on crée une nouvelle variable
 			VarSymbol declarationVar = new VarSymbol(new ArrayType(Type.INT,Integer.parseInt($INTEGER.text)), $IDENT.text, $symTab.getScope());  

 			//On ajoute la variable dans la table des symboles
			$symTab.insert($IDENT.text, declarationVar);

			$code = Code3aGenerator.genDecl(declarationVar);
		}
;

