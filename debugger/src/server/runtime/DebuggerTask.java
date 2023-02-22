package server.runtime;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import common.IClientService;

public class DebuggerTask {

	private SimpleScriptEvaluator evaluator;

	public DebuggerTask(Path program, int[] breakpoints, ExecutorService executorService, IClientService clientService) {
		executorService.submit(() -> {
			try {
				SimpleScriptLexer lexer = new SimpleScriptLexer(CharStreams.fromPath(program));
				SimpleScriptParser parser = new SimpleScriptParser(new CommonTokenStream(lexer));
				DebuggerTask.this.evaluator = new SimpleScriptEvaluator(program.toFile().getName(), breakpoints, clientService);
		        evaluator.visit(parser.program());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public SimpleScriptEvaluator getEvaluator() {
		return evaluator;
	}
}
