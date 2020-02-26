package lv.kid.vermut.intellij.yaml.editor;

import com.intellij.application.options.CodeStyleAbstractConfigurable;
import com.intellij.application.options.CodeStyleAbstractPanel;
import com.intellij.application.options.TabbedLanguageCodeStylePanel;
import com.intellij.openapi.options.Configurable;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider;
import com.intellij.psi.codeStyle.CustomCodeStyleSettings;
import lv.kid.vermut.intellij.yaml.YamlLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NeonCodeStyleSettingsProvider extends CodeStyleSettingsProvider {
    @Override
    public AnsibleCodeStyleSettings createCustomSettings(final CodeStyleSettings settings) {
        return new AnsibleCodeStyleSettings(settings);
    }

    @NotNull
    @Override
    public Configurable createSettingsPage(final CodeStyleSettings settings, final CodeStyleSettings originalSetting) {
        return new CodeStyleAbstractConfigurable(settings, originalSetting, getConfigurableDisplayName()) {
            @Nullable
            @Override
            public String getHelpTopic() {
                return null;
            }

            @Override
            protected CodeStyleAbstractPanel createPanel(final CodeStyleSettings settings) {
                return new SimpleCodeStyleMainPanel(getCurrentSettings(), settings);
            }
        };
    }

    private static class SimpleCodeStyleMainPanel extends TabbedLanguageCodeStylePanel {
        public SimpleCodeStyleMainPanel(final CodeStyleSettings currentSettings, final CodeStyleSettings settings) {
            super(YamlLanguage.INSTANCE, currentSettings, settings);
        }
    }

    private class AnsibleCodeStyleSettings extends CustomCodeStyleSettings {
        public AnsibleCodeStyleSettings(final CodeStyleSettings settings) {
            super("AnsibleCodeStyleSettings", settings);
        }
    }

    @Override
    public String getConfigurableDisplayName() {
        return YamlLanguage.INSTANCE.getDisplayName();
    }
}

