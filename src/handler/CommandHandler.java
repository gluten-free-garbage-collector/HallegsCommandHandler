package handler;

import java.util.LinkedList;
import java.util.List;

import commandManagement.CommandManager.CommandClass;
import converter.ConverterConvertException;
import mehtod.MehtodHandler;

public abstract class CommandHandler {
	protected String name;
	protected List<MehtodHandler> methods;
	protected List<SubCommand> handler;
	protected String[] alias;

	public CommandHandler(Class<?> clazz, CommandClass anno) {
		super();
		this.name = anno.name();
		this.alias = anno.alias();
		methods = new LinkedList<>();
		handler = new LinkedList<>();
	}

	public void addMethod(MehtodHandler method) {
		methods.add(method);
	}

	public void addCommand(SubCommand command) {
		handler.add(command);
	}

	public boolean command(String[] args, int offset) throws ConverterConvertException {
		if (offset >= args.length) {
			return false;
		}

		if (!args[offset].equals(name)) {
			return false;
		}

		for (SubCommand e : this.handler) {
			if (e.command(args, offset + 1)) {
				return true;
			}
		}

		for (MehtodHandler m : methods) {
			if (m.command(args, offset + 1)) {
				return true;
			}
		}
		return false;
	}

	public List<String> complete(String[] args, int offset) {
		List<String> ret = new LinkedList<>();

		if (offset == args.length) {
			ret.add(this.name);
			return ret;
		}

		if (!args[offset].equals(name)) {
			return ret;
		}

		for (SubCommand e : this.handler) {
			ret.addAll(e.complete(args, offset + 1));
		}

		for (MehtodHandler e : this.methods) {
			ret.addAll(e.complete(args, offset + 1));
		}
		return ret;
	}

	public String printTree(String pre, boolean last) {

		String out = "\n" + pre + "+- " + name + " [" + String.join(" ", alias) + "]";
		if (last) {
			pre = pre + "   ";
		} else {
			pre = pre + "|  ";
		}
		for (int i = 0; i < handler.size(); i++) {
			out += handler.get(i).printTree(pre, (i >= handler.size() - 1 && methods.isEmpty()));
		}

		for (int i = 0; i < methods.size(); i++) {
			out += methods.get(i).printTree(pre);
		}
		return out;
	}
}
