package plugin;

import org.bukkit.plugin.java.JavaPlugin;

import commandManagement.CommandManager;
import handler.builder.BaseCommandBuilder;
import handler.builder.MethodBuilder;
import handler.builder.SubCommandBuilder;
import testCommands.BoolCommand;

public class TestPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		try {
			CommandManager.manage(new SubCommandBuilder(), new BaseCommandBuilder(),
					new MethodBuilder(), BoolCommand.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
