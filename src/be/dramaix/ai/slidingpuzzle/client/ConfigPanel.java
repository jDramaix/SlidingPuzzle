/*
 * Copyright 2011 Julien Dramaix.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package be.dramaix.ai.slidingpuzzle.client;

import static be.dramaix.ai.slidingpuzzle.client.MySelectors.SELECTOR;
import static com.google.gwt.query.client.GQuery.$;
import be.dramaix.ai.slidingpuzzle.shared.AlgorithmType;
import be.dramaix.ai.slidingpuzzle.shared.HeuristicType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Element;

/**
 * Class managing the ui of the configuration of the puzzle
 * 
 * @author Julien Dramaix (https://plus.google.com/u/0/103916508880440628637)
 * 
 */
public class ConfigPanel {

	public interface Templates extends SafeHtmlTemplates {

		Templates INSTANCE = GWT.create(Templates.class);

		@Template("<option value=\"{0}\">{1}</option>")
		SafeHtml option(String value, String label);

		@Template("<option value=\"{0}\" selected=\"selected\">{1}</option>")
		SafeHtml selectedOption(String value, String label);
	}

	private HeuristicType heuristicType;
	private AlgorithmType algorithmType;

	public ConfigPanel() {
		// Default config
		algorithmType = AlgorithmType.IDA_STAR;
		heuristicType = HeuristicType.PATTERN_DATABASE;

		init();

		bind();
	}

	public AlgorithmType getAlgorithmType() {
		return algorithmType;
	}

	public HeuristicType getHeuristicType() {
		return heuristicType;
	}

	private void bind() {
		// heuristic choice
		SELECTOR.getHeuristicSelectElement().change(new Function() {
			public void f(Element e) {
				String value = $(e).val();
				onHeuristicChange(value);
			}
		});

		// algorithm choice
		SELECTOR.getAlgorithmSelectElement().change(new Function() {
			public void f(Element e) {
				String value = $(e).val();
				onAlgorithmChange(value);
			}
		});

		SELECTOR.getPuzzleTypeRadios().click(new Function() {
			public void f(Element e) {
				int newDimension = Integer.parseInt($(e).val());
				onDimensionChange(newDimension);
			}
		});

	}

	private void onDimensionChange(int newDimension) {
		GQuery algorithmSelector = SELECTOR.getAlgorithmSelectElement();
		GQuery algorithmSelectorSpan = algorithmSelector.siblings("span");
		GQuery heuristicSelectorRow = SELECTOR.getHeuristicSelectElement()
				.parent().parent();

		if (newDimension == 4) {
			algorithmType = AlgorithmType.IDA_STAR;

			algorithmSelector.val(AlgorithmType.IDA_STAR.name());
			algorithmSelector.hide();
			algorithmSelectorSpan.text(AlgorithmType.IDA_STAR.getLabel());

			heuristicSelectorRow.show();
		} else {
			algorithmSelectorSpan.text("");
			algorithmSelector.show();

		}

		SELECTOR.getSolveButton().hide();
		SELECTOR.getPlayButton().hide();

	}

	private void init() {
		// build algorithm options
		SafeHtmlBuilder algorithmTypeOptions = new SafeHtmlBuilder();

		for (AlgorithmType at : AlgorithmType.values()) {
			algorithmTypeOptions.append(Templates.INSTANCE.option(at.name(),
					at.getLabel()));
		}

		GQuery algorithmSelector = SELECTOR.getAlgorithmSelectElement();
		algorithmSelector.append(algorithmTypeOptions.toSafeHtml().asString());
		algorithmSelector.val(algorithmType.name());

		// build heuristic options
		SafeHtmlBuilder heuristicTypeOptions = new SafeHtmlBuilder();

		for (HeuristicType ht : HeuristicType.values()) {
			heuristicTypeOptions.append(Templates.INSTANCE.option(ht.name(),
					ht.getLabel()));
		}

		GQuery heuristicSelector = SELECTOR.getHeuristicSelectElement();
		heuristicSelector.append(heuristicTypeOptions.toSafeHtml().asString());
		heuristicSelector.val(heuristicType.name());

		onDimensionChange(4);
	}

	private void onAlgorithmChange(String algorithm) {
		algorithmType = AlgorithmType.valueOf(algorithm);

		GQuery heuristicSelectorRow = SELECTOR.getHeuristicSelectElement()
				.parent().parent();

		if (algorithmType == AlgorithmType.BDF) {
			heuristicSelectorRow.hide();
		} else {
			heuristicSelectorRow.show();
		}

	}

	private void onHeuristicChange(String type) {
		heuristicType = HeuristicType.valueOf(type);
	}

}
