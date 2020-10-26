// Generated from /media/kasra/my files/kasra/learning/computer/compiler/project/COOL compiler/src/grammar/COOL.g4 by ANTLR 4.7.2

import coolExternalClasses.*;
import coolExternalClasses.TableObject;
import javafx.scene.control.Tab;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;

/**
 * This class provides an empty implementation of {@link COOLListener},
 * which can be extended to create a listener which only needs to handle a subset
 * of the available methods.
 */
public class COOLBaseListener implements COOLListener {
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	Scope currentScope;
	@Override public void enterProgram(COOLParser.ProgramContext ctx) {
		currentScope = new Scope(null);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitProgram(COOLParser.ProgramContext ctx) {
		if (currentScope.getSymbolTable().lookup("main") == null) {
			System.out.println("Error104 : in line [" + ctx.start.getLine() + "], can not find main method");
		} else {
			boolean found = false;
			for (TableObject i : currentScope.getSymbolTable().lookup("main")) {
				if (i.getKind() == Kind.METHOD) {
					found = true;
					break;
				}
			}
			if (!found) {
				System.out.println("Error104 : in line [" + ctx.start.getLine() + "], can not find main method");
			}
		}
		currentScope = currentScope.getParent();
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterClasses(COOLParser.ClassesContext ctx) {
		currentScope = new Scope(currentScope);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitClasses(COOLParser.ClassesContext ctx) {
		currentScope = currentScope.getParent();
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterEof(COOLParser.EofContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitEof(COOLParser.EofContext ctx) {}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */

	/**TODO: incomplete method*/
	@Override public void enterClassDefine(COOLParser.ClassDefineContext ctx) {
		String className = ctx.className.getText();
		String parentName = ctx.parentName.getText();
		TableObject classObject = new TableObject(className, null, Kind.CLASS, ctx.getStart().getLine());
		classObject.setParent(parentName);
		currentScope.getSymbolTable().insert(className, classObject);

		Scope iterScope = currentScope;
		boolean parentFound = false;
		// check if the super class is defined or not
		while (!parentFound && iterScope != null) {
			ArrayList<TableObject> searchList = iterScope.getSymbolTable().lookup(parentName);
			for (TableObject i : searchList) {
				if (i.getKind() == Kind.CLASS) {
					parentFound = true;
					break;
				}
			}
			iterScope = iterScope.getParent();
		}
		if (!parentFound) {
			System.out.println("Error 105.2 in Line ["
					+ ctx.getStart().getLine() + "], can not find super class "
					+ parentName);
		} else {
			//TODO: FIND LOOP IN SUPER CLASSES
			iterScope = currentScope;
			String currentSuper = parentName;
			ArrayList<String> parents = new ArrayList<>();

			boolean seen = false;
			String errorText = "Error108 : invalid inheritance ";
			for (String s: parents) {
				if (s.equals(currentSuper)) {
					seen = true;
				}
			}
			if (seen) {
				for (String s : parents) {
					errorText = errorText.concat(s + "=>");
				}
			}
			while (iterScope != null) {
				if (iterScope.getSymbolTable().lookup(currentSuper) != null) {
					boolean foundInST = false;
					for (TableObject i : iterScope.getSymbolTable().lookup(currentSuper)) {
						if (i.getKind() == Kind.CLASS) {
							foundInST = true;
							parents.add(currentSuper);
							currentSuper = i.getName();
							break;
						}
					}
					if (!foundInST) {
						iterScope = iterScope.getParent();
					}
				} else {
					iterScope = iterScope.getParent();
				}
			}

		}

		// CHECK IF className HAS BEEN DEFINED ALREADY OR NOT
		if (currentScope.getSymbolTable().lookup(className) != null) {
			System.out.println("Error101 : in line " + ctx.getStart().getLine() + ", " + className
					+ "has been defined already");
			currentScope.getSymbolTable().insert(className, classObject);
		} else {
			boolean found  = false;
			for (TableObject i : currentScope.getSymbolTable().lookup(className)) {
				if (i.getKind() == Kind.CLASS) {
					found = true;
					break;
				}
			}
			if (found) {
				System.out.println("Error101 : in line [" + ctx.getStart().getLine() + "], " + className
						+ "has been defined already");
			}

		}
		currentScope = new Scope(currentScope);
		currentScope.setOwner(classObject);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitClassDefine(COOLParser.ClassDefineContext ctx) {
		currentScope = currentScope.getParent();
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterMethod(COOLParser.MethodContext ctx) {
		String methodName = ctx.featureName.getText();
		TableObject methodObject = new TableObject(methodName, Type.DEFINITION, Kind.METHOD, ctx.getStart().getLine());
		currentScope.getSymbolTable().insert(methodName, methodObject);

		// check for duplicate method name
		if (currentScope.getSymbolTable().lookup(methodName) != null) {
			if (currentScope.getSymbolTable().lookup(methodName) != null) {
				System.out.println("Error102 : in line [" + methodObject.getLineNumber() + "], " + methodName
						+ "has been defined already in " + currentScope.getOwner().getName());
			}
		}
		currentScope = new Scope(currentScope);
		currentScope.setOwner(methodObject);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitMethod(COOLParser.MethodContext ctx) {
		currentScope = currentScope.getParent();
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</	p>
	 */
	@Override public void enterProperty(COOLParser.PropertyContext ctx) {
		String propertyName = ctx.featureName.getText();
		TableObject propertyObject = new TableObject(propertyName, Type.DEFINITION, Kind.VARIABLE, ctx.getStart().getLine());
		currentScope.getSymbolTable().insert(propertyName, propertyObject);

		// check for duplicate method name
		if (currentScope.getSymbolTable().lookup(propertyName) != null) {
			System.out.println("Error102 : in line [" + propertyObject.getLineNumber() + "], " + propertyName
					+ "has been defined already in " + currentScope.getOwner().getName());
		}
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitProperty(COOLParser.PropertyContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterFormal(COOLParser.FormalContext ctx) {
		String argName = ctx.argName.getText();
		TableObject argObject = new TableObject(argName, typeStrToEnum(ctx.argType.getText()), Kind.VARIABLE, ctx.start.getLine());
		currentScope.getSymbolTable().insert(argName, argObject);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitFormal(COOLParser.FormalContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterLetIn(COOLParser.LetInContext ctx) {
		String varName1 = ctx.expName.getText();
		String varType1 = ctx.expType.getText();
		String varName2 = ctx.expName2.getText();
		String varType2 = ctx.expType2.getText();

		TableObject varObject1 = new TableObject(varName1, typeStrToEnum(varType1), Kind.VARIABLE, ctx.start.getLine());
		if (currentScope.getSymbolTable().lookup(varName1) == null) {
			currentScope.getSymbolTable().insert(varName1, varObject1);
		} else {
			System.out.println("Error 103 : in line " + ctx.start.getLine() + " " + varName1 + "has been defined already");
			currentScope.getSymbolTable().insert(varName1, varObject1);
		}

		TableObject varObject2 = new TableObject(varName2, typeStrToEnum(varType2), Kind.VARIABLE, ctx.start.getLine());
		if (currentScope.getSymbolTable().lookup(varName2) == null) {
			currentScope.getSymbolTable().insert(varName2, varObject2);
		} else {
			System.out.println("Error 103 : in line " + ctx.start.getLine() + " " + varName2 + "has been defined already");
			currentScope.getSymbolTable().insert(varName2, varObject2);
		}
		currentScope = new Scope(currentScope);

	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitLetIn(COOLParser.LetInContext ctx) {currentScope = currentScope.getParent(); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterMinus(COOLParser.MinusContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitMinus(COOLParser.MinusContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterString(COOLParser.StringContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitString(COOLParser.StringContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterIsvoid(COOLParser.IsvoidContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitIsvoid(COOLParser.IsvoidContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterWhile(COOLParser.WhileContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitWhile(COOLParser.WhileContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterDivision(COOLParser.DivisionContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitDivision(COOLParser.DivisionContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterNegative(COOLParser.NegativeContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitNegative(COOLParser.NegativeContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterBoolNot(COOLParser.BoolNotContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitBoolNot(COOLParser.BoolNotContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterLessThan(COOLParser.LessThanContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitLessThan(COOLParser.LessThanContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterBlock(COOLParser.BlockContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitBlock(COOLParser.BlockContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterId(COOLParser.IdContext ctx) {
		String varName = ctx.expName.getText();
		TableObject varObject = new TableObject(varName, Type.DEFINITION, Kind.VARIABLE, ctx.start.getLine());
		if (currentScope.getSymbolTable().lookup(varName) != null) {
			System.out.println("Error103 : in line " + ctx.start.getLine() + " " + varName + "has been defined already");
		}
		currentScope.getSymbolTable().insert(varName, varObject);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitId(COOLParser.IdContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterMultiply(COOLParser.MultiplyContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitMultiply(COOLParser.MultiplyContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterIf(COOLParser.IfContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitIf(COOLParser.IfContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterCase(COOLParser.CaseContext ctx) {
		//TODO: miscomprehension in functionality of the CASE rule
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitCase(COOLParser.CaseContext ctx) {currentScope=currentScope.getParent(); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterOwnMethodCall(COOLParser.OwnMethodCallContext ctx) {
		String methodName = ctx.expName.getText();
		if (currentScope.getSymbolTable().lookup(methodName) == null) {
			System.out.println("Error106 : in line " + ctx.start.getLine() + ", can not find " + methodName);
		} else {
			boolean found = false;
			for (TableObject i : currentScope.getSymbolTable().lookup(methodName)) {
				if (i.getKind() == Kind.METHOD) {
					found = true;
					break;
				}
			}
			if (!found) {
				System.out.println("Error106 : in line " + ctx.start.getLine() + ", can not find " + methodName);

			}
		}
		currentScope = new Scope(currentScope);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitOwnMethodCall(COOLParser.OwnMethodCallContext ctx) {currentScope = currentScope.getParent(); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterAdd(COOLParser.AddContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitAdd(COOLParser.AddContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterNew(COOLParser.NewContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitNew(COOLParser.NewContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterParentheses(COOLParser.ParenthesesContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitParentheses(COOLParser.ParenthesesContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterAssignment(COOLParser.AssignmentContext ctx) {
		String varName = ctx.expName.getText();
		if (currentScope.getSymbolTable().lookup(varName) == null) {
			System.out.println("Error107 : in line " + ctx.start.getLine() + " " + "can not find " + varName);
		}
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitAssignment(COOLParser.AssignmentContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterFalse(COOLParser.FalseContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitFalse(COOLParser.FalseContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterInt(COOLParser.IntContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitInt(COOLParser.IntContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterEqual(COOLParser.EqualContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitEqual(COOLParser.EqualContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterTrue(COOLParser.TrueContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitTrue(COOLParser.TrueContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterLessEqual(COOLParser.LessEqualContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitLessEqual(COOLParser.LessEqualContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterMethodCall(COOLParser.MethodCallContext ctx) {
		String methodName = ctx.expName.getText();
		if (currentScope.getSymbolTable().lookup(methodName) == null) {
			System.out.println("Error106 : in line " + ctx.start.getLine() + ", can not find " + methodName);
		} else {
			boolean found = false;
			for (TableObject i : currentScope.getSymbolTable().lookup(methodName)) {
				if (i.getKind() == Kind.METHOD) {
					found = true;
					break;
				}
			}
			if (!found) {
				System.out.println("Error106 : in line " + ctx.start.getLine() + ", can not find " + methodName);
			}
		}
		currentScope = new Scope(currentScope);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitMethodCall(COOLParser.MethodCallContext ctx) {
		currentScope = currentScope.getParent();
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterEveryRule(ParserRuleContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitEveryRule(ParserRuleContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void visitTerminal(TerminalNode node) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void visitErrorNode(ErrorNode node) { }

	public Type typeStrToEnum(String type) {
		switch (type) {
			case "Int":
				return Type.INT;
			case "String":
				return Type.STRING;
			case "Float":
				return Type.FLOAT;
			case "Boolean":
				return Type.BOOLEAN;
			case "Double":
				return Type.DOUBLE;
			default:
				return Type.OBJECT;
		}
	}
}