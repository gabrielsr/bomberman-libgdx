package br.unb.unbomber;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.StageSpec;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class JsonEncodeTest {

	public static final String STAGE_NAME = "Stage Teste";
	Entity character;
	StageSpec stage;

	@Before
	public void setUp() throws Exception {

		stage = new StageSpec();

		stage.setName(STAGE_NAME);

		stage.setMapRepresentation("##########" + "##@@    ##");

		character = new Entity();
		stage.getEntities().add(character);

		CellPlacement placement = new CellPlacement();
		placement.setCellX(4);
		placement.setCellY(3);

		character.addComponent(placement);
	}

	@Test
	public void encodeCharAsJsonTest() {
		Json json = new Json();
		System.out.println(json.prettyPrint(stage));

	}

	@Test
	public void decodeCharAsJson() {
		Json json = new Json();

		FileHandle stageFile = Gdx.files.local("stage.json");

		StageSpec stage2 = json.fromJson(StageSpec.class, stageFile.reader());

		assertEquals(STAGE_NAME, stage2.getName());
	}

}
