package repository;

import model.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvUserRepository implements repository.UserRepository {
	private final String filePath = "users.csv";

	public CsvUserRepository() {
		// ensure file exists
		try {
			File f = new File(filePath);
			if (!f.exists()) f.createNewFile();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public User findByLogin(String login) {
		if (login == null) return null;
		List<User> all = findAll();
		for (User u : all) {
			if (login.equals(u.getLogin())) return u;
		}
		return null;
	}

	@Override
	public List<User> findAll() {
		List<User> result = new ArrayList<>();
		try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty()) continue;
				String[] parts = line.split(",");
				User u = new User();
				try { u.setId(Integer.parseInt(parts[0])); } catch (Exception ignored) {}
				if (parts.length > 1) u.setLogin(parts[1]);
				if (parts.length > 2) u.setPassword(parts[2]);
				if (parts.length > 3) { try { u.setAge(Integer.parseInt(parts[3])); } catch (Exception ignored) {} }
				if (parts.length > 4) u.setSex(parts[4]);
				if (parts.length > 5) u.setLanguage(parts[5]);
				if (parts.length > 6) u.setServer(parts[6]);
				result.add(u);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public synchronized void save(User user) {
		if (user == null) throw new IllegalArgumentException("user is null");
		List<User> all = findAll();
		int maxId = 0;
		for (User u : all) if (u.getId() > maxId) maxId = u.getId();
		if (user.getId() <= 0) user.setId(maxId + 1);

		String line = String.format("%d,%s,%s,%d,%s,%s,%s", user.getId(), escape(user.getLogin()), escape(user.getPassword()), user.getAge(), escape(user.getSex()), escape(user.getLanguage()), escape(user.getServer()));
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
			bw.write(line);
			bw.newLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String escape(String s) {
		if (s == null) return "";
		return s.replace("\n", " ").replace(",", ";");
	}
}


