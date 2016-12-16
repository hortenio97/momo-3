package io.ph.bot.commands;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.ph.bot.model.Permission;
/**
 * Syntax for commands
 * TODO: Eventually replace Command error messages with the content of these annotations
 * @author Paul
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandSyntax {
	/**
	 * Default command usage
	 * @return
	 */
	String defaultSyntax();
	/**
	 * String array of aliases
	 * @return Aliases
	 */
	String[] aliases();
	/**
	 * Permission needed
	 * @return Permission
	 */
	Permission permission();
	/**
	 * Description of the command
	 * @return Description
	 */
	String description();
	/**
	 * Example WITHOUT default syntax OR server prefix
	 * Those get added in the help command
	 * @return Example of parameters + contents
	 */
	String example();
}
