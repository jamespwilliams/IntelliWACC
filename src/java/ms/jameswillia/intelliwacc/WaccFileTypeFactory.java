package org.antlr.jetbrains.wacc;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class WaccFileTypeFactory extends FileTypeFactory {
	@Override
	public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
		fileTypeConsumer.consume(WaccFileType.INSTANCE, WaccFileType.FILE_EXTENSION);
	}
}
