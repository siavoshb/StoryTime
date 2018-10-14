package FlyLife;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class StoryToD3 {

	public static class D3Node {
		String id;
		int group;
		public D3Node(int id, int group) {
			this.id = String.valueOf(id);
			this.group = group;
		}
	}

	public static class D3Link {
		String source, target;
		public D3Link(int source, int target) {
			this.source = String.valueOf(source);
			this.target = String.valueOf(target);
		}
	}

	public static class D3NodeData {
		List<D3Node> nodes = new ArrayList<>();
		List<D3Link> links = new ArrayList<>();;
	}

	public StoryToD3() {
	}

	public void saveAsJson(Set<Fly> flies) throws IOException {
		D3NodeData data = new D3NodeData();

		for (Fly f : flies) {
			data.nodes.add(new D3Node(f.id, f.generation));
			if (f.parentId > 0) {
				data.links.add(new D3Link(f.id, f.parentId));
			}
		}

		try (Writer writer = new FileWriter("display/graph.json")) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(data, writer);
		}
	}

}
