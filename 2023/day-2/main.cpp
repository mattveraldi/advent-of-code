#include <iostream>
#include <fstream>
#include <string>
#include <regex>

int main()
{
    std::fstream file("input.txt", std::fstream::in);
    const int red_limit = 12;
    const int green_limit = 13;
    const int blue_limit = 14;
    const std::regex id_reg("(\\d+)", std::regex_constants::ECMAScript);
    const std::regex color_reg("(red|green|blue)");
    char character;
    int current_game_id = -1;
    bool is_game_valid = true;
    int result = 0;
    std::string token = "";
    while (file >> std::noskipws >> character)
    {
        // am i parsing the game id?
        if (character == ':')
        {
            std::smatch id_match;
            std::regex_search(token, id_match, id_reg);
            current_game_id = atoi(id_match[1].str().c_str());
            token = "";
        }
        // is the end of the round, a cube set that I saw, or the end of the game?
        else if (character == ',' || character == ';' || character == '\n')
        {
            // I treat end of the round and cube set that I saw the same.
            // the concept is that you can't extract more cubes than the limit.
            // evaluate number and color and update colors only if the game is still valid
            if (is_game_valid)
            {
                std::smatch id_match;
                std::smatch color_match;
                std::regex_search(token, id_match, id_reg);
                std::regex_search(token, color_match, color_reg);
                const std::string color = color_match[1];
                const int increment = atoi(id_match[1].str().c_str());
                if (color == "red")
                    is_game_valid = increment <= red_limit;
                if (color == "green")
                    is_game_valid = increment <= green_limit;
                if (color == "blue")
                    is_game_valid = increment <= blue_limit;
            }

            // am I approaching the end of this game?
            if (character == '\n')
            {
                if (current_game_id == -1)
                {
                    std::cerr << "Parsing error: current_game_id is -1";
                    exit(1);
                }
                if (is_game_valid)
                {
                    result += current_game_id;
                }
                // next game
                is_game_valid = true;
                current_game_id = -1;
                token = "";
            }

            token = "";
        }
        // continue parsing...
        else
        {
            token += character;
        }
    }
    if (result != 2377)
    {
        std::cerr << "That's not the right answer!"
                  << "\n";
        exit(1);
    }
    else
    {
        std::cout << "That's the right answer!"
                  << "\n";
    }
    return 0;
}